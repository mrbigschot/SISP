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
    double orientation; // given in degrees, stored in radians, 0 is along +x axis

    Neighborhood nhood;

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
        this.nhood = n;
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
        if (Globals.DEBUG) {
            System.out.println(nhood.toString());
        }
        update();
        move();
    }

    public void update() {
        avoidCollision();
//        matchVel();
        towardCenter();
    }

    private void avoidCollision() {
        int xmin = Math.max(0, nhood.getCenter()[0] - 5);
        int xmax = Math.min(nhood.getWidth(), nhood.getCenter()[0] + 5);
        int ymin = Math.max(0, nhood.getCenter()[1] - 5);
        int ymax = Math.min(nhood.getWidth(), nhood.getCenter()[1] + 5);
        for (int ix = xmin; ix < xmax; ix++) {
            for (int iy = ymin; iy < ymax; iy++) {
                if (!(ix == nhood.getCenter()[0] && iy == nhood.getCenter()[1]) && (nhood.getGrid()[ix][iy] == 2)) {
                    addToAngle(.1);
                }
            }
        }
    }

    private void matchVel() {
        double avgSpd = swarmVel();
        if (avgSpd > speed) {
            speed += .1;
        } else {
            if (avgSpd < speed) {
                speed -= .1;
            } else {

            }
        }
        double avgAng = swarmAng();
        if (avgAng > orientation) {
            orientation += .1;
        } else {
            if (avgAng < orientation) {
                orientation -= .1;
            } else {

            }
        }
    }

    private double swarmVel() {
        double sum = 0;
        for (Bug neighbor : theSwarm) {
            if (!neighbor.equals(this)) {
                sum += neighbor.getSpeed();
            }
        }
        return sum / (theSwarm.size() - 1);
    }

    private double swarmAng() {
        double sum = 0;
        for (Bug neighbor : theSwarm) {
            if (!neighbor.equals(this)) {
                sum += neighbor.getAngle();
            }
        }
        return sum / (theSwarm.size() - 1);
    }

    private void towardCenter() {
        double xsum = 0;
        double ysum = 0;
        int count = 0;
        for (int ix = 0; ix < nhood.getWidth(); ix++) {
            for (int iy = 0; iy < nhood.getHeight(); iy++) {
                if (!(ix == nhood.getCenter()[0] && iy == nhood.getCenter()[1]) && (nhood.getGrid()[ix][iy] == 2)) {
                    xsum += ix;
                    ysum += iy;
                    count++;
                }
            }
        }
        if (count != 0) {
            double xavg = xsum / count;
            double yavg = ysum / count;
            double a = xavg - nhood.getCenter()[0];
            double b = yavg - nhood.getCenter()[1];
            double angle = Math.atan2(b, a);
            double d1 = Math.abs(angle - orientation);
            double d2 = Math.abs(orientation - angle);
            if (d2 > d1) {
                addToAngle(-.1);
            } else {
                addToAngle(.1);
            }
        }
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
        int gx = (int) (nhood.getCenter()[0] + dx);
        int gy = (int) (nhood.getCenter()[1] + dy);
        return nhood.getGrid()[gx][gy] == 1;
    }

    @Override
    public String toString() {
        String returnMe = "Bug: (" + x + ", " + y + ")\n";

        returnMe += "angle = " + orientation + "\n";
        returnMe += "speed = " + speed;

        return returnMe;

    }

}
