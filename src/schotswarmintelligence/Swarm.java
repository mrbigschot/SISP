/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.util.ArrayList;

public class Swarm extends ArrayList<Bug> {

    SIModel theModel;

    public Swarm(SIModel m) {
        theModel = m;
    }

    public void initialize() {
        this.clear();
        if (Globals.TEST) {
            testInit();
        } else {
            for (int i = 0; i < Globals.SWARM_SIZE; i++) {
                int x = Globals.random(200, 300);
                int y = Globals.random(200, 300);
                double rand = Globals.random(0.0, 1.0);
                Bug bug = new Bug(this, x, y);
                bug.setAngle(Globals.random(0, 2 * Math.PI));
                bug.setSpeed(1);
                if (rand < .33) {
                    // matcher
                    bug.setWeights(0, 1, 0);
                } else {
                    if (rand < .66) {
                        // condenser
                        bug.setWeights(0, 0, 1);
                    } else {
                        // explorer
                        bug.setWeights(1, 0, 0);
                    }
                }

                this.add(bug);
            }
        }
    }

    private void testInit() {
//        Bug b1 = new Bug(this, 250, 250);
//        b1.setAngle(Math.PI);
//        b1.setSpeed(1.0);
//        b1.index = 1;
//        this.add(b1);
//        Bug b2 = new Bug(this, 253, 250);
//        b2.setAngle(0.0);
//        b2.setSpeed(1.0);
//        b2.index = 2;
//        this.add(b2);
        for (int i = 0; i < Globals.SWARM_SIZE; i++) {
                int x = Globals.random(200, 300);
                int y = Globals.random(200, 300);
                double rand = Globals.random(0, 1);
                Bug bug = new Bug(this, x, y);
                bug.setAngle(Globals.random(0, 2 * Math.PI));
                bug.setSpeed(1);
                bug.setWeights(Globals.AVOID_WEIGHT, Globals.MATCH_WEIGHT, Globals.CONDENSE_WEIGHT);
                this.add(bug);
        }
    }

    public void updateLocation(int x, int y) {
        theModel.updateLocation(x, y, 2);
    }

}
