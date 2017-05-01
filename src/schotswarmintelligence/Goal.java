/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Goal extends SIObject {

    int mass;
    double speed;
    double angle;

    public Goal() {
        mass = Globals.MASS;
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
    
    public void reset() {
        mass = Globals.MASS;
    }

    @Override
    public void paint(Graphics g) {
        if (mass == 0) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(0, 150, 0));
        }
        int rad = (int) (mass / 50.0);
        g.fillOval((int) x - rad, (int) y - rad, rad, rad);
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
