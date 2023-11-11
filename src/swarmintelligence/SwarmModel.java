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
import gui.ViewMode;

public class SwarmModel implements Modelable {

    private final Environment environment;
    private final Swarm swarmA, swarmB;
    private boolean controlMode = false;
    private boolean contestMode = false;
    private ViewMode viewMode = ViewMode.NORMAL;
    private boolean manualResourcePlacement = false;

    public SwarmModel() {
        this.environment = new Environment(this);
        this.swarmA = new Swarm(this, Environment.ENV_SWARM_A);
        this.swarmA.configure(new SwarmConfiguration("data"));
        this.swarmB = new Swarm(this, Environment.ENV_SWARM_B);
        this.swarmB.configure(new SwarmConfiguration("data"));
        initialize(false);
    }

    // Accessors
    public Environment getEnvironment() { return this.environment; }
    public Neighborhood getNeighborhood(int id, double x, double y) { return this.environment.getNeighborhood(id, x, y); }
    public Swarm getSwarmA() { return this.swarmA; }
    public Swarm getSwarmB() { return this.swarmB; }
    public boolean isControlMode() { return this.controlMode; }
    public void setControlMode(boolean value) { this.controlMode = value; }
    public void toggleControlMode() { this.controlMode = !this.controlMode; }
    public boolean isContestMode() { return this.contestMode; }
    public void setContestMode(boolean value) { this.contestMode = value; }
    public void toggleContestMode() { this.contestMode = !this.contestMode; }
    public void setViewMode(ViewMode value) { this.viewMode = value; }
    public void setManualResourcePlacement(boolean value) { this.manualResourcePlacement = value; }
    public boolean isManualResourcePlacement() { return this.manualResourcePlacement; }

    private void initialize(boolean lockHives) {
        this.swarmA.init(this.environment, lockHives);
        this.swarmB.init(this.environment, lockHives);
        if (this.isContestMode()) {
            this.environment.setSwarms(this.swarmA, this.swarmB);
        } else {
            this.environment.setSwarms(this.swarmA, null);
        }
    }

    // restart the simulation while keeping all variables unchanged
    public void restart() {
        resetSwarm(this.swarmA);
        resetSwarm(this.swarmB);
        this.environment.restart(true, true);
        initialize(true);
    }

    // restart the simulation, changing appropriate variables
    public void reset(boolean lockHives, boolean lockResources, boolean lockWalls) {
        resetSwarm(this.swarmA);
        resetSwarm(this.swarmB);
        this.environment.restart(lockResources, lockWalls);
        initialize(lockHives);
    }

    private void resetSwarm(Swarm swarm) {
        if (swarm != null) {
            swarm.setControlMode(this.controlMode);
            swarm.reset();
        }
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

    @Override
    public void step() {
        environment.step();
        environment.clean();
        swarmA.step();
        if (this.isContestMode()) {
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
        environment.paint(g, this.viewMode);
        if (this.viewMode == ViewMode.NORMAL) {
            swarmA.paint(g);
            if (this.isContestMode()) swarmB.paint(g);
        }
    }
    
    public int[] getTotals() {
        int[] result = new int[2];
        result[0] = swarmA.getResourceTotal();
        if (this.isContestMode()) result[1] = swarmB.getResourceTotal();
        return result;
    }

    public void save() {
        if (this.isContestMode()) {
//            MyWriter mw = new MyWriter("data");
//            boolean awins;
//            awins = swarmA.getTotal() > swarmB.getTotal();
//            mw.print(Globals.saveString(awins));
//            mw.close();
        }
    }

}