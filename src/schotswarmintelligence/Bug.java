/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Bug extends SIObject {

    Swarm theSwarm;
    double speed;
    double orientation; // given in degrees, stored in radians, 0 is along +x axis, PI/2 is along +y axis

    Neighborhood nHoodC, nHoodP;

    public Bug() {

    }

    public Bug(Swarm s, double x, double y) {
        theSwarm = s;
        this.x = x;
        this.y = y;
    }

    public void setSpeed(double s) {
        this.speed = s;
    }

    public void addToSpeed(double d) {
        if ((speed + d < 0)) {
            speed = 0;
        } else {
            if (speed + d > Globals.MAX_SPEED) {
                speed = Globals.MAX_SPEED;
            } else {
                speed += d;
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setAngle(double a) {
        orientation = a * Math.PI / 180;
    }

    public void addToAngle(double a) {
        orientation += a;
        if (orientation >= (2 * Math.PI)) {
            orientation -= (2 * Math.PI);
        } else {
            if (orientation > 0) {
                orientation += (2 * Math.PI);
            }
        }
    }

    public double getAngle() {
        return orientation;
    }

    public void setNeighborhood(Neighborhood n) {
        this.nHoodP = this.nHoodC;
        this.nHoodC = n;
        if (nHoodP == null) {
            nHoodP = nHoodC;
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, 3, 3);
    }

    public void updateLocation() {
        theSwarm.updateLocation((int) x, (int) y);
    }

    public void step() {
        update();
        move();
    }

    public void update() {
//        avoidCollision();
        matchVel();
//        towardCenter();
    }

    private void avoidCollision() {
        int xmin = Math.max(0, nHoodC.getCenter()[0] - 5);
        int xmax = Math.min(nHoodC.getWidth(), nHoodC.getCenter()[0] + 5);
        int ymin = Math.max(0, nHoodC.getCenter()[1] - 5);
        int ymax = Math.min(nHoodC.getWidth(), nHoodC.getCenter()[1] + 5);
        for (int ix = xmin; ix < xmax; ix++) {
            for (int iy = ymin; iy < ymax; iy++) {
                if (!(ix == nHoodC.getCenter()[0] && iy == nHoodC.getCenter()[1]) && (nHoodC.getGrid()[ix][iy] == 2)) {
                    addToAngle(.3);
                }
            }
        }
    }

    private void matchVel() {
        double[] d = calcVel();
        if (d[2] != 0) {
            double dAngle = orientation - d[3];
            if (dAngle < Math.PI) {
                if (dAngle < 0) {
                    if (dAngle < -Math.PI) {
                        addToAngle(-.2);
                    } else {
                        // -pi < d < 0
                        addToAngle(.2);
                    }
                } else {
                    // 0 < d < pi
                    addToAngle(-.2);
                }
            } else {
                // pi < d
                addToAngle(.2);
            }

            double dSpeed = d[2] - speed;
            if (dSpeed > 0) {
                addToSpeed(.1);
            } else {
                if (dSpeed < 0) {
                    addToSpeed(.1);
                }
            }

            if (Globals.DEBUG) {
                System.out.println("dSpeed = " + dSpeed);
                System.out.println("dAngle = " + dAngle);
            }
        }
    }

    private double[] calcVel() {
        double[] comC = centerOfMass(nHoodC);
        double[] comP = centerOfMass(nHoodP);
        double dx = comC[0] - comP[0];
        double dy = comC[1] - comP[1];
        double mag = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double angle = Math.atan2(dy, dx);
        if (Globals.DEBUG) {
            System.out.println("My position = (" + x + ", " + y + ")");
            System.out.println("My speed = " + speed);
            System.out.println("COM = (" + comC[0] + ", " + comC[1] + ")");
            System.out.println("dX: " + dx);
            System.out.println("dY: " + dy);
            System.out.println("mag = " + mag);
            System.out.println("angle = " + angle);
            System.out.println("\n");
        }
        double[] returnMe = new double[4];
        returnMe[0] = dx;
        returnMe[1] = dy;
        returnMe[2] = mag;
        returnMe[3] = angle;
        return returnMe;
    }

    private void towardCenter() {
        double[] com = centerOfMass(nHoodC);
        if (!((com[0] == nHoodC.getCenter()[0]) && (com[1] == nHoodC.getCenter()[1]))) {
            double a = com[0] - nHoodC.getCenter()[0];
            double b = com[1] - nHoodC.getCenter()[1];
            double angle = Math.atan2(b, a);
            double d = orientation - angle;
            if (Globals.DEBUG) {
                System.out.println("POS = (" + x + ", " + y + ")");
                System.out.println("COM = (" + com[0] + ", " + com[1] + ")");
                System.out.println("Relative COM = (" + a + ", " + b + ")");
                System.out.println("angle = " + angle);
                System.out.println("angle - orient = " + d);
            }
            if (d < Math.PI) {
                if (d < 0) {
                    if (d < -Math.PI) {
                        addToAngle(-.1);
                    } else {
                        // -pi < d < 0
                        addToAngle(.1);
                    }
                } else {
                    // 0 < d < pi
                    addToAngle(-.1);
                }
            } else {
                // pi < d
                addToAngle(.1);
            }
        }
    }

    private double[] centerOfMass(Neighborhood grid) {
        double[] returnMe = new double[2];
        double xsum = 0;
        double ysum = 0;
        int count = 0;
        for (int ix = 0; ix < grid.getWidth(); ix++) {
            for (int iy = 0; iy < grid.getHeight(); iy++) {
                if (!(ix == grid.getCenter()[0] && iy == grid.getCenter()[1]) && (grid.getGrid()[ix][iy] == 2)) {
                    xsum += ix;
                    ysum += iy;
                    count++;
                }
            }
        }
        if (count != 0) {
            double xavg = xsum / count;
            double yavg = ysum / count;
            returnMe[0] = xavg;
            returnMe[1] = yavg;
        } else {
            returnMe[0] = grid.getCenter()[0];
            returnMe[1] = grid.getCenter()[1];
        }
        return returnMe;
    }

    public void move() {
        double nx = x + (speed * Math.cos(orientation));
        double ny = y + (speed * Math.sin(orientation));
        if (facingWall(nx, ny)) {
            orientation += 1;
        } else {
            x = nx;
            y = ny;
        }
    }

    private boolean facingWall(double nx, double ny) {
        double dx = nx - x;
        double dy = ny - y;
        int gx = (int) (nHoodC.getCenter()[0] + dx);
        int gy = (int) (nHoodC.getCenter()[1] + dy);
        return nHoodC.getGrid()[gx][gy] == 1;
    }

    @Override
    public String toString() {
        String returnMe = "Bug: (" + x + ", " + y + ")\n";

        returnMe += "angle = " + orientation + "\n";
        returnMe += "speed = " + speed;

        return returnMe;

    }

}
