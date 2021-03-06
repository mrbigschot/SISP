package swarmintelligence;

import io.MyWriter;
import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    SIGrid theGrid;
    Swarm swarmA, swarmB;
    int step = 0;

    public SIModel() {
        swarmA = new Swarm(this, 8);
        if (Globals.CONTEST) {
            swarmB = new Swarm(this, 7);
        }
        theGrid = new SIGrid();
        initialize();
    }

    public void updateLocation(int x, int y, int value) {
        theGrid.setValue(x, y, value);
    }

    public void setPher(int x, int y, int val) {
        theGrid.setPher(x, y, val);
    }

    public void restart() {
        swarmA = new Swarm(this, 8);
        if (Globals.CONTEST) {
            swarmB = new Swarm(this, 7);
        }
        theGrid.restart();
        initialize();
    }

    public void reset() {
        swarmA = new Swarm(this, 8);
        if (Globals.CONTEST) {
            swarmB = new Swarm(this, 7);
        }
        theGrid = new SIGrid();
        initialize();
    }

    public void initialize() {
        swarmA.init(theGrid);
        theGrid.setSwarmA(swarmA);
        if (Globals.CONTEST) {
            swarmB.init(theGrid);
            theGrid.setSwarmB(swarmB);
        }
    }

    public Neighborhood getNeighborhood(int id, double x, double y) {
        return theGrid.getNeighborhood(id, x, y);
    }

    @Override
    public void step() {
        theGrid.step();
        theGrid.clean();
        swarmA.step();
        if (Globals.CONTEST) {
            swarmB.step();
        }
    }

    @Override
    public boolean isDone() {
        if (Globals.NUM_GOALS != 0) {
            int swSum = swarmA.getTotal();
            if (swarmB != null) {
                swSum += swarmB.getTotal();
            }
            return swSum == Globals.totalMass();
        }
        return false;
    }

    public void paint(Graphics g) {
        theGrid.paint(g);
        if (!Globals.PHEREMODE) {
            swarmA.paint(g);
            if (Globals.CONTEST) {
                swarmB.paint(g);
            }
        }
    }

    public void save() {
        if (Globals.CONTEST) {
            MyWriter mw = new MyWriter("data");
            boolean awins;
            awins = swarmA.getTotal() > swarmB.getTotal();
            mw.print(Globals.saveString(awins));
            mw.close();
        }
    }

}
