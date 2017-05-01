package swarmintelligence;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Swarm extends ArrayList<Bug> {

    SIModel theModel;
    int hX, hY;
    Color color;
    int total;
    int swarmID;

    public Swarm(SIModel m, int id) {
        theModel = m;
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

    public int getTotal() {
        return total;
    }

    public int getX() {
        return hX;
    }

    public int getY() {
        return hY;
    }

    public void init(SIGrid g) {
        this.clear();
        hX = Globals.random(100, Globals.WIDTH - 100);
        hY = Globals.random(100, Globals.HEIGHT - 100);
        for (int i = 0; i < Globals.SWARM_SIZE; i++) {
            spawn();
        }
    }

    public void spawn() {
        double rand = Globals.random(0.0, 1.0);
        Bug bug = new Bug(this, hX, hY, color);
        bug.setAngle(Globals.random(0, 2 * Math.PI));
        bug.setSpeed(1);
        if (swarmID == 8) {
            if (rand < Globals.A_EXPLORE_RATE) {
                // explorer
                bug.setWeights(Globals.A_TOP_WEIGHT, Globals.A_MID_WEIGHT, Globals.A_LOW_WEIGHT);
            } else {
                if (rand < Globals.A_CONDENSE_RATE + Globals.A_EXPLORE_RATE) {
                    // condenser
                    bug.setWeights(Globals.A_LOW_WEIGHT, Globals.A_MID_WEIGHT, Globals.A_TOP_WEIGHT);
                } else {
                    // matcher
                    bug.setWeights(Globals.A_LOW_WEIGHT, Globals.A_TOP_WEIGHT, Globals.A_MID_WEIGHT);
                }
            }
        } else {
            if (rand < Globals.B_EXPLORE_RATE) {
                // explorer
                bug.setWeights(Globals.B_TOP_WEIGHT, Globals.B_MID_WEIGHT, Globals.B_LOW_WEIGHT);
            } else {
                if (rand < Globals.B_CONDENSE_RATE + Globals.B_EXPLORE_RATE) {
                    // condenser
                    bug.setWeights(Globals.B_LOW_WEIGHT, Globals.B_MID_WEIGHT, Globals.B_TOP_WEIGHT);
                } else {
                    // matcher
                    bug.setWeights(Globals.B_LOW_WEIGHT, Globals.B_TOP_WEIGHT, Globals.B_MID_WEIGHT);
                }
            }
        }
        this.add(bug);
    }

    public void deposit(int x) {
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
        for (Bug bug : this) {
            bug.paint(g);
        }
        if (swarmID == 8) {
            g.fillRect(10, 525, (int) (500.0 * total / Globals.totalMass()), 10);
            g.drawString("" + total + "/" + Globals.totalMass(), 520, 535);
        } else {
            g.fillRect(10, 545, (int) (500.0 * total / Globals.totalMass()), 10);
            g.drawString("" + total + "/" + Globals.totalMass(), 520, 555);
        }
    }

    public void step() {
        for (Bug bug : this) {
            bug.updateLocation();
        }
        for (Bug bug : this) {
            bug.setNeighborhood(theModel.getNeighborhood(swarmID, bug.getX(), bug.getY()));
            bug.step();
        }
    }

}
