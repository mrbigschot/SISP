 /*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Swarm extends ArrayList<Bug> {

    SIModel theModel;
    int hX, hY;
    Color color;
    int resource, total;
    int swarmID;

    public Swarm(SIModel m, int id) {
        theModel = m;
        resource = 0;
        total = 0;
        swarmID = id;
        if (swarmID == 8) {
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return hX;
    }

    public int getY() {
        return hY;
    }

    public void init(SIGrid g) {
        this.clear();
        if (!Globals.TEST) {
            hX = Globals.random(100, Globals.WIDTH - 100);
            hY = Globals.random(100, Globals.HEIGHT - 100);
            for (int i = 0; i < Globals.SWARM_SIZE; i++) {
                spawn();
            }
        } else {
            testInit();
        }
    }

    public void testInit() {
        hX = 100;
        hY = 100;
        for (int i = 0; i < Globals.SWARM_SIZE; i++) {
            int x = Globals.random(hX - 50, hX + 50);
            int y = Globals.random(hY - 50, hY + 50);
            Bug bug = new Bug(this, x, y, color);
            bug.setAngle(Globals.random(0, 2 * Math.PI));
            bug.setSpeed(1);
            bug.setWeights(Globals.AVOID_WEIGHT, Globals.MATCH_WEIGHT, Globals.CONDENSE_WEIGHT);
            this.add(bug);
        }
    }

    public void spawn() {
        double rand = Globals.random(0.0, 1.0);
        Bug bug = new Bug(this, hX, hY, color);
        bug.setAngle(Globals.random(0, 2 * Math.PI));
        bug.setSpeed(1);
        if (rand < Globals.EXPLORE_RATE) {
            // explorer
            bug.setWeights(1, 0, 0);
        } else {
            if (rand < Globals.CONDENSE_RATE + Globals.EXPLORE_RATE) {
                // condenser
                bug.setWeights(0, 0, 1);
            } else {
                // matcher
                bug.setWeights(0, 1, 0);
            }
        }
        this.add(bug);
    }

    public void deposit(int x) {
        resource += x;
        total += x;
    }

    public void updateLocation(int x, int y) {
        theModel.updateLocation(x, y, 2);
    }

    public void setPher(int x, int y, int val) {
        theModel.setPher(x, y, val);
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval(hX - 5, hY - 5, 10, 10);
        synchronized (Globals.GOD) {
            for (Bug bug : this) {
                bug.paint(g);
            }
        }
    }

    public void step() {
//        if (resource > 10) {
//            spawn();
//            resource = 0;
//        }
        synchronized (Globals.GOD) {
            for (Bug bug : this) {
                bug.updateLocation();
            }
            for (Bug bug : this) {
                bug.setNeighborhood(theModel.getNeighborhood(swarmID, bug.getX(), bug.getY()));
                bug.step();
            }
        }
    }

    public int gather() {
        return theModel.gather();
    }

}
