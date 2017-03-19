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

    public void testInit() {
        for (int i = 0; i < Globals.SWARM_SIZE; i++) {
                int x = Globals.random(200, 300);
                int y = Globals.random(200, 300);
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
    
    public void setPher(int x, int y, int val) {
        theModel.setPher(x, y, val);
    }

}
