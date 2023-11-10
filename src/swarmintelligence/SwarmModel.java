package swarmintelligence;

// Java imports
import java.awt.Graphics;

// Swarm imports
import environment.Neighborhood;
import environment.Environment;
import bugs.Swarm;
import bugs.SwarmConfiguration;
import mvc.Modelable;
import pheromone.PheromoneChannel;

public class SwarmModel implements Modelable {

    private final Environment environment;
    private final Swarm swarmA, swarmB;

    public SwarmModel() {
        this.environment = new Environment(this);
        this.swarmA = new Swarm(this, Environment.ENV_SWARM_A);
        this.swarmA.configure(new SwarmConfiguration("data"));
        this.swarmB = new Swarm(this, Environment.ENV_SWARM_B);
        this.swarmB.configure(new SwarmConfiguration("data"));
        initialize(false);
    }
    
    private void initialize(boolean lockHives) {
        this.swarmA.init(this.environment, lockHives);
        this.swarmB.init(this.environment, lockHives);
        if (Globals.CONTEST) {
            this.environment.setSwarms(this.swarmA, this.swarmB);
        } else {
            this.environment.setSwarms(this.swarmA, null);
        }
    }

    public Environment getEnvironment() { return this.environment; }

    public Swarm getSwarmA() { return this.swarmA; }
    public Swarm getSwarmB() { return this.swarmB; }

    // restart the simulation while keeping all variables unchanged
    public void restart() {
        if (this.swarmA != null) this.swarmA.reset();
        if (this.swarmB != null) this.swarmB.reset();
        this.environment.restart(true, true);
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
        if (this.swarmA != null) this.swarmA.reset();
        if (this.swarmB != null) this.swarmB.reset();
        this.environment.restart(lockResources, lockWalls);
        initialize(lockHives);
    }
    
    public void updateLocation(int x, int y, int value) {
        environment.setValue(x, y, value);
    }

    public void emitPheromone(int x, int y, int val, PheromoneChannel channel) {
        environment.emitPheromone(x, y, val, channel);
    }

    public void createResource(int x, int y) {
        environment.addResourceAt(x, y);
    }
    
    public Neighborhood getNeighborhood(int id, double x, double y) {
        return environment.getNeighborhood(id, x, y);
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
        if (this.environment.getSettings().getResourceCount() > 0) {
            int swSum = swarmA.getResourceTotal();
            if (swarmB != null) {
                swSum += swarmB.getResourceTotal();
            }
            return swSum == this.getTotalResourceMassAvailable();
        }
        return false;
    }
    public int getTotalResourceMassAvailable() { return this.environment.totalResourceMassAvailable(); }

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
        result[0] = swarmA.getResourceTotal();
        if (Globals.CONTEST) {
            result[1] = swarmB.getResourceTotal();
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