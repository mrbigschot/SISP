/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Bug extends SIObject {

    Swarm theSwarm;
    double velocity;
    double orientation; // given in degrees, stored in radians, 0 is along +x axis

    int[][] neighborhood;

    public Bug() {

    }

    public Bug(Swarm s, double x, double y) {
        theSwarm = s;
        this.x = x;
        this.y = y;
    }

    public void setVel(double v) {
        this.velocity = v;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setAngle(double a) {
        orientation = a * Math.PI / 180;
    }

    public double getAngle() {
        return orientation;
    }

    public void setNeighborhood(int[][] b) {
        this.neighborhood = b;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, 3, 3);
    }

    public void step() {
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
//        String returnMe = "";
//        for (int i = 0; i < neighborhood.length; i++) {
//            for (int j = 0; j < neighborhood[0].length; j++) {
//                returnMe += neighborhood[i][j];
//            }
//            returnMe += "\n";
//        }
//        System.out.println(returnMe);
        if (facingWall()) {
            orientation += .1;
        }
        
    }
    
    private boolean facingWall() {
        return false;
    }
    

    private void matchVel() {
        double avgVel = swarmVel();
        if (avgVel > velocity) {
            velocity += .1;
        } else {
            if (avgVel < velocity) {
                velocity -= .1;
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
                sum += neighbor.getVelocity();
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
        x = x + (velocity * Math.cos(orientation));
        y = y + (velocity * Math.sin(orientation));
    }

    @Override
    public String toString() {
        String returnMe = "Bug: \n";

        returnMe += "angle = " + orientation + "\n";
        returnMe += "v = " + velocity;

        return returnMe;

    }

}
