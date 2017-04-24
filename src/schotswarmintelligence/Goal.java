/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Goal extends SIObject implements Grabbable {

    int mass;
    double speed;
    double angle;

    public Goal() {
        mass = 500;
        if (Globals.TEST) {
            this.x = 400;
            this.y = 400;
        } else {
            this.x = Globals.random(10, Globals.WIDTH - 10);
            this.y = Globals.random(10, Globals.HEIGHT - 10);
        }
    }

    public Goal(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(50, 150, 0));
        g.fillOval((int) x, (int) y, 5, 5);
    }

    @Override
    public void applyForce(double dir, double f, int m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean isDepleted() {
        return mass == 0;
    }

    public int gather() {
        if (mass > 0) {
            mass--;
            return 1;
        }
        return 0;
    }

}
