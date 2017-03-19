/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Goal extends SIObject {

    public Goal() {
        if (Globals.TEST) {
            this.x = 300;
            this.y = 300;
        } else {
            this.x = Globals.random(10, Globals.WIDTH - 10);
            this.y = Globals.random(10, Globals.HEIGHT - 10);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval((int) x, (int) y, 5, 5);
    }

}
