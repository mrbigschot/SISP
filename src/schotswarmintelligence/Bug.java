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

    public void step() {
//        System.out.println(this.toString());
//        System.out.println(nhood.toString());
        update();
        move();
//        theSwarm.updateLocation((int) x, (int) y);
    }

    public void update() {
        avoidWalls();
        matchVel();
        matchAng();
    }

    private void avoidWalls() {
    }

    private boolean facingWall(double nx, double ny) {
        double dx = nx - x;
        double dy = ny - y;
        int gx = (int) (nhood.getCenter()[0] + dx);
        int gy = (int) (nhood.getCenter()[1] + dy);
        return nhood.getGrid()[gx][gy] == 1;
    }

    private void matchVel() {
        double avgVel = swarmVel();
        if (avgVel > speed) {
            speed += .1;
        } else {
            if (avgVel < speed) {
                speed -= .1;
            } else {

            }
        }
    }

    private void matchAng() {
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

    @Override
    public String toString() {
        String returnMe = "Bug: (" + x + ", " + y + ")\n";

        returnMe += "angle = " + orientation + "\n";
        returnMe += "speed = " + speed;

        return returnMe;

    }

}
