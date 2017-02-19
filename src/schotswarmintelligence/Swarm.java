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
        for (int i = 0; i < 15; i++) {
            int x = Globals.random(10, 90);
            int y = Globals.random(10, 90);
            Bug bug = new Bug(this, x, y);
            bug.setAngle(Globals.random(0, 360));
            bug.setSpeed(1);
            this.add(bug);
        }
    }
    
    public void updateLocation(int x, int y) {
        theModel.updateLocation(x, y, 2);
    }
    
}
