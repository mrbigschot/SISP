package swarmintelligence;

import environment.Neighborhood;
import environment.SIGrid;
import bugs.Swarm;
import bugs.SwarmConfiguration;
import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    private SIGrid theGrid;
    public Swarm swarmA, swarmB;
    private int step = 0;

    public SIModel() {
        swarmA = new Swarm(this, 8);
        swarmA.configure(new SwarmConfiguration("data"));
        if (Globals.CONTEST) {
            swarmB = new Swarm(this, 7);
            swarmB.configure(new SwarmConfiguration("data"));
        }
        theGrid = new SIGrid(this);
        initialize(false);
    }

    public void updateLocation(int x, int y, int value) {
        theGrid.setValue(x, y, value);
    }

    public void emitPheremone(int x, int y, int val, int channel) {
        theGrid.emitPheremone(x, y, val, channel);
    }

    public void restart() {
        swarmA.reset();
        if (Globals.CONTEST) {
            if (swarmB == null) {
                swarmB = new Swarm(this, 7);
                swarmB.configure(new SwarmConfiguration("data"));
            }
            swarmB.reset();
        }
        theGrid.restart(true, true);
        initialize(true);
    }

    public void reset(boolean lockHives, boolean lockResources, boolean lockWalls) {
//        synchronized (swarmA) {
//            swarmA.reset();
//            if (Globals.CONTEST) {
//                synchronized (swarmB) {
//                    swarmB.reset();
//                }
//            }
//        }
        swarmA.reset();
        if (Globals.CONTEST) {
            if (swarmB == null) {
                swarmB = new Swarm(this, 7);
                swarmB.configure(new SwarmConfiguration("data"));
            }
            swarmB.reset();
        }
        theGrid.restart(lockResources, lockWalls);
        initialize(lockHives);
    }

    public void initialize(boolean lockHives) {
        swarmA.init(theGrid, lockHives);
        theGrid.setSwarmA(swarmA);
        if (Globals.CONTEST) {
            swarmB.init(theGrid, lockHives);
            theGrid.setSwarmB(swarmB);
        }
    }

    public void createResource(int x, int y) {
        theGrid.addResource(x, y);
    }
    
    public Neighborhood getNeighborhood(int id, double x, double y, int pherChannel) {
        return theGrid.getNeighborhood(id, x, y, pherChannel);
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
        if (!Globals.PHEREMODE1 && !Globals.PHEREMODE2 && !Globals.PHEREMODE3) {
            swarmA.paint(g);
            if (Globals.CONTEST) {
                swarmB.paint(g);
            }
        }
    }
    
    public int[] getTotals() {
        int[] result = new int[2];
        result[0] = swarmA.getTotal();
        if (Globals.CONTEST) {
            result[1] = swarmB.getTotal();
        }
        return result;
    }

    public void save() {
        if (Globals.CONTEST) {
//            MyWriter mw = new MyWriter("data");
//            boolean awins;
//            awins = swarmA.getTotal() > swarmB.getTotal();
//            mw.print(Globals.saveString(awins));
//            mw.close();
        }
    }

}