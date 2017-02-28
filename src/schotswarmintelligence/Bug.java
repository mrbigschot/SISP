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
    double accel;
    double orientation; // radians, 0 is along +x axis, PI/2 is along +y axis
    double addSpeed;
    double addAngle;

    double wAvoid = 1;
    double wCondense = 1;
    double wMatch = 1;

    int index;
    int turnAway;

    Neighborhood nHoodC, nHoodP;

    public Bug() {

    }

    public Bug(Swarm s, double x, double y) {
        this();
        theSwarm = s;
        this.x = x;
        this.y = y;
    }

    public Bug(Swarm s, double x, double y, double wA, double wM, double wC) {
        this(s, x, y);
        wAvoid = wA;
        wMatch = wM;
        wCondense = wC;
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
        orientation = a;
        if (orientation < 0) {
            orientation = (orientation % (2 * Math.PI)) + (2 * Math.PI);
        } else {
            orientation = orientation % (2 * Math.PI);
        }
    }

    public void addToAngle(double a) {
        orientation += a;
        if (orientation < 0) {
            orientation = (orientation % (2 * Math.PI)) + (2 * Math.PI);
        } else {
            orientation = orientation % (2 * Math.PI);
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
        addSpeed = 0;
        addAngle = 0;
        avoidCollision();
        matchVel();
        condense();
        addToAngle(addAngle);
        addToSpeed(addSpeed);
    }

    private void avoidCollision() {
        double rAngle = 0;
        double[] com = centerOfMass2(nHoodC);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = (Math.atan2(com[1], com[0])) + Math.PI;
            if (angle < 0) {
                angle = (angle % (2 * Math.PI)) + (2 * Math.PI);
            } else {
                angle = angle % (2 * Math.PI);
            }
            if (Globals.DEBUG) {
                System.out.println("B" + index);
                System.out.println("\tPOS: (" + x + ", " + y + ")");
                System.out.println("\tCOM:(" + com[0] + ", " + com[1] + ")");
                System.out.println("\tθ: " + angle);
            }
            rAngle = matchAngle(angle);
        }
        addAngle += wAvoid * rAngle;
    }

    private void matchVel() {
        double rAngle = 0;
        double rSpeed = 0;
        double[] d = calcVel();
        if (d[2] != 0) {
            // if (angle (d[3]) is in the Bug's "shadow") {
            //     if (can go slower) {
            //         slow down
            //     } else {
            //         turn toward angle
            //     }
            // } else {
            //     turn toward angle
            //     match speed
            // }
            double diffAngle = Math.abs(orientation - d[3]);
            if (Globals.DEBUG) {
                System.out.println("diffAngle: " + diffAngle);
            }
            if ((diffAngle == Math.PI)) {
//            if ((diffAngle < 5 * Math.PI / 4) && (diffAngle > 3 * Math.PI / 4)) {
                if (speed > 0) {
                    rSpeed = -.1;
                } else {
                    rAngle = matchAngle(d[3]);
                }
            } else {
                rAngle = matchAngle(d[3]);
                rSpeed = matchSpeed(d[2]);
            }
        }
        addAngle += wMatch * rAngle;
        addSpeed += wMatch * rSpeed;
    }

    private double[] calcVel() {
        double[] comC = centerOfMass1(nHoodC);
        double[] comP = centerOfMass1(nHoodP);
        double dx = comC[0] - comP[0];
        double dy = comC[1] - comP[1];
        double mag = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double angle = Math.atan2(dy, dx);
        if (Globals.DEBUG) {
            System.out.println("B" + index);
            System.out.println("\tPOS: (" + nHoodC.cX + ", " + nHoodC.cY + ")");
            System.out.println("\tspeed: " + speed);
            System.out.println("\tangle: " + orientation);
            System.out.println("COM = (" + comC[0] + ", " + comC[1] + ")");
            System.out.println("\tdX: " + dx);
            System.out.println("\tdY: " + dy);
            System.out.println("\tmag = " + mag);
            System.out.println("\tangle = " + angle);
            System.out.println("\n");
        }
        double[] returnMe = new double[4];
        returnMe[0] = dx;
        returnMe[1] = dy;
        returnMe[2] = mag;
        returnMe[3] = angle;
        return returnMe;
    }

    private void condense() {
        double rAngle = 0;
        double[] com = centerOfMass1(nHoodC);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = Math.atan2(com[1], com[0]);
            rAngle = matchAngle(angle);
            if (Globals.DEBUG) {
                System.out.println("B" + index + " condense");
                System.out.println("\tPOS: (" + x + ", " + y + ")");
                System.out.println("\tCOM:(" + com[0] + ", " + com[1] + ")");
                System.out.println("\tθ: " + angle);
                System.out.println("adjust by: " + rAngle);
            }
        }
        addAngle += wCondense * rAngle;
    }

    // centerOfMass() returns the center of mass relative to this Bug's position
    private double[] centerOfMass1(Neighborhood grid) {
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
            returnMe[0] = xavg - grid.getCenter()[0];
            returnMe[1] = yavg - grid.getCenter()[1];
        } else {
            returnMe[0] = 0;
            returnMe[1] = 0;
        }
        return returnMe;
    }

    private double[] centerOfMass2(Neighborhood grid) {
        double[] returnMe = new double[2];
        double xsum = 0;
        double ysum = 0;
        int count = 0;
        int xmin = Math.max(0, grid.getCenter()[0] - 5);
        int xmax = Math.min(grid.getWidth(), grid.getCenter()[0] + 5);
        int ymin = Math.max(0, grid.getCenter()[1] - 5);
        int ymax = Math.min(grid.getWidth(), grid.getCenter()[1] + 5);
        for (int ix = xmin; ix < xmax; ix++) {
            for (int iy = ymin; iy < ymax; iy++) {
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
            returnMe[0] = xavg - grid.getCenter()[0];
            returnMe[1] = yavg - grid.getCenter()[1];
        } else {
            returnMe[0] = 0;
            returnMe[1] = 0;
        }
        return returnMe;
    }

    private double matchAngle(double a) {
        double delta = orientation - a; // -2pi to 2pi
        if (delta < Math.PI) {
            if (delta < 0) {
                if (delta < -Math.PI) {
                    return -.1;
                } else {
                    return .1;
                }
            } else {
                return -.1;
            }
        } else {
            return .1;
        }
    }

    private double matchSpeed(double s) {
        if (speed < s) {
            return .1;
        } else {
            return -.1;
        }
    }

    public void move() {
        double nx = x + (speed * Math.cos(orientation));
        double ny = y + (speed * Math.sin(orientation));
        if (facingWall(nx, ny)) {
//            if (turnAway == 0) {
//                if (Globals.coinFlip(.5)) {
//                    turnAway = 1;
//                } else {
//                    turnAway = -1;
//                }
//            }
//            addToAngle(turnAway * .1);
            addToAngle(Math.PI);
        } else {
//            turnAway = 0;
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
