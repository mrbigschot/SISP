package swarmintelligence;

import environment.Neighborhood;
import environment.Environment;
import bugs.Swarm;
import bugs.SwarmConfiguration;
import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    private Environment environment = null;
    public Swarm swarmA, swarmB;

    public SIModel() {
        environment = new Environment(this);
        swarmA = new Swarm(this, Swarm.SWARM_A);
        swarmA.configure(new SwarmConfiguration("data"));
        swarmB = new Swarm(this, Swarm.SWARM_B);
        swarmB.configure(new SwarmConfiguration("data"));
        initialize(false);
    }
    
    private void initialize(boolean lockHives) {
        swarmA.init(environment, lockHives);
        swarmB.init(environment, lockHives);
        if (Globals.CONTEST) {
            environment.setSwarms(swarmA, swarmB);
        } else {
            environment.setSwarms(swarmA, null);
        }
    }
    
    // restart the simulation while keeping all variables unchanged
    public void restart() {
        swarmA.reset();
        if (Globals.CONTEST) {
            swarmB.reset();
        }
        environment.restart(true, true);
        initialize(true);
    }

    // restart the simulation, changing appropriate variables
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
            swarmB.reset();
        }
        environment.restart(lockResources, lockWalls);
        initialize(lockHives);
    }
    
    public void updateLocation(int x, int y, int value) {
        environment.setValue(x, y, value);
    }

    public void emitPheremone(int x, int y, int val, int channel) {
        environment.emitPheremone(x, y, val, channel);
    }

    public void createResource(int x, int y) {
        environment.addResource(x, y);
    }
    
    public Neighborhood getNeighborhood(int id, double x, double y, int pherChannel) {
        return environment.getNeighborhood(id, x, y, pherChannel);
    }

    @Override
    public void step() {
        environment.step();
        environment.clean();
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
        environment.paint(g);
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