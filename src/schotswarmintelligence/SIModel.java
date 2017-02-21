package schotswarmintelligence;

import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    SIGrid theGrid;
    Swarm theSwarm;

    public SIModel() {
        theSwarm = new Swarm(this);
        theSwarm.initialize();
        theGrid = new SIGrid();
    }

    public void updateLocation(int x, int y, int value) {
        theGrid.setValue(x, y, value);
    }

    @Override
    public void step() {
        theGrid.clean();
        for (Bug bug : theSwarm) {
            bug.updateLocation();
        }
        for (Bug bug : theSwarm) {
            bug.setNeighborhood(theGrid.getNeighborhood(bug.getX(), bug.getY()));
            bug.step();
        }
    }

    public void paint(Graphics g) {
        theGrid.paint(g);
        for (Bug bug : theSwarm) {
            bug.paint(g);
        }
    }

}
