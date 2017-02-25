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
                Bug bug = new Bug(this, x, y, Globals.AVOID_WEIGHT, Globals.MATCH_WEIGHT, Globals.CONDENSE_WEIGHT);
                bug.setAngle(Globals.random(0, 2 * Math.PI));
                bug.setSpeed(1);
                this.add(bug);
            }
        }
    }

    private void testInit() {
//        Bug b1 = new Bug(this, 250, 250);
//        b1.setAngle(3.5);
//        b1.setSpeed(1.0);
//        b1.index = 1;
//        this.add(b1);
//        Bug b2 = new Bug(this, 260, 250);
//        b2.setAngle(2);
//        b2.setSpeed(1.4);
//        b2.index = 2;
//        this.add(b2);
        for (int i = 0; i < 20; i++) {
                int x = Globals.random(200, 300);
                int y = Globals.random(200, 300);
                Bug bug = new Bug(this, x, y);
                bug.setAngle(0);
                bug.setSpeed(1);
                this.add(bug);
            }
    }

    public void updateLocation(int x, int y) {
        theModel.updateLocation(x, y, 2);
    }

}
