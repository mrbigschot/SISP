package schotswarmintelligence;

import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    SIGrid theGrid;
    Swarm theSwarm;
    int step = 0;

    public SIModel() {
        theSwarm = new Swarm(this);
        theGrid = new SIGrid();
        initialize();
    }

    public void updateLocation(int x, int y, int value) {
        theGrid.setValue(x, y, value);
    }
    
    public void setPher(int x, int y, int val) {
        theGrid.setPher(x, y, val);
    }

    public void reset() {
        theSwarm = new Swarm(this);
        theGrid = new SIGrid();
        initialize();
    }
    
    public void initialize() {
        theSwarm.clear();
        if (Globals.TEST) {
            theSwarm.testInit();
        } else {
            for (int i = 0; i < Globals.SWARM_SIZE; i++) {
                int x = Globals.random(200, 300);
                int y = Globals.random(200, 300);
                while (theGrid.isWall(x, y)) {
                    x = Globals.random(200, 300);
                    y = Globals.random(200, 300);
                }
                double rand = Globals.random(0.0, 1.0);
                Bug bug = new Bug(theSwarm, x, y);
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
                theSwarm.add(bug);
            }
        }
    }
    
    @Override
    public void step() {
        step++;
        theGrid.clean();
        for (Bug bug : theSwarm) {
            bug.updateLocation();
        }
        boolean b = false;
        if (step % 1 == 0) { // affects clumping
            b = true;
            step = 0;
        }
        for (Bug bug : theSwarm) {
            bug.setNeighborhood(theGrid.getNeighborhood(bug.getX(), bug.getY()));
            bug.step(b);
        }
    }

    public void paint(Graphics g) {
        theGrid.paint(g);
        for (Bug bug : theSwarm) {
            bug.paint(g);
        }
    }

}
