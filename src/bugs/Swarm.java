package bugs;

// Java imports
import java.awt.Color;
import java.awt.Graphics;
import java.util.Enumeration;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

// Swarm imports
import environment.Environment;
import pheromone.PheromoneChannel;
import swarmintelligence.SwarmModel;
import swarmintelligence.SwarmUtilities;

public class Swarm implements ISwarm {

    private final SwarmModel swarmModel;
    private int hiveX, hiveY;
    private final Color color;
    private final int swarmID;
    private SwarmConfiguration config;
    private final Bugs bugs;
    private int resourceTotal;

    public Swarm(SwarmModel m, int id) {
        this.swarmModel = m;
        this.resourceTotal = 0;
        this.swarmID = id;
        if (this.swarmID == Environment.ENV_SWARM_A) {
            this.color = SWARM_COLOR_A;
        } else {
            this.color = SWARM_COLOR_B;
        }
        this.bugs = new Bugs();
    }

    public void configure(SwarmConfiguration val) {
        this.config = val;
    }
    public void configure(Enumeration<BugConfiguration> val) {
        this.config = new SwarmConfiguration();
        while (val.hasMoreElements()) {
            this.config.add(val.nextElement());
        }
    }
    
    public SwarmConfiguration getConfiguration() { 
        return this.config;
    }
    
    public Color getColor() {
        return this.color;
    }

    public int getResourceTotal() {
        return this.resourceTotal;
    }

    public int getX() {
        return this.hiveX;
    }
    public int getY() {
        return this.hiveY;
    }
    public int getSwarmID() {
        return this.swarmID;
    }
    
    public void init(Environment env, boolean lockPosition) {
        this.resourceTotal = 0;
        this.bugs.clear();
        if (!lockPosition) {
            do {
                hiveX = SwarmUtilities.random(100, Environment.ENVIRONMENT_WIDTH - 100);
                hiveY = SwarmUtilities.random(100, Environment.ENVIRONMENT_HEIGHT - 100);
            } while (env.isWallAt(hiveX, hiveY));
        }
        spawn();
    }
    
    public void reset() {
        this.resourceTotal = 0;
        this.bugs.clear();
        spawn();
    }

    public void spawn() {
        if (this.config != null) {
            int bugCount = 0;
            for (BugConfiguration bugConfiguration : this.config) {
                for (int ii = 0; ii < bugConfiguration.getSize(); ii++) {
                    Bug bug = new Bug(this, hiveX, hiveY, color, bugCount);
                    bug.setAngle(SwarmUtilities.randomAngle());
                    bug.setSpeed(1);
                    bugConfiguration.configure(bug);
                    this.bugs.add(bug);
                }
            }
        }
    }
    
//    public void spawnBug() {
//        if (config != null) {
//            BugConfiguration bc = config.get(0);
//            Bug bug = new Bug(this, hX, hY, color);
//            bug.setAngle(Globals.random(0, 2 * Math.PI));
//            bug.setSpeed(1);
//            bug.configure(bc);
//            this.add(bug);
//        }
//    }

    public void deposit(int x) {
        this.resourceTotal += x;
    }

    public void updateLocation(int x, int y) {
        this.swarmModel.updateLocation(x, y, 2);
    }

    public void emitPheromone(int x, int y, int val, PheromoneChannel channel) {
        this.swarmModel.emitPheromone(x, y, val, channel);
    }

    public PheromoneChannel getPheromoneChannel() {
        if (this.swarmID == Environment.ENV_SWARM_A) {
            return PheromoneChannel.HIVE_A;
        } else if (this.swarmID == Environment.ENV_SWARM_B) {
            return PheromoneChannel.HIVE_B;
        }
        return null;
    }
    
    public void paint(Graphics g) {
        int r = 8;
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(color);
        g2.drawOval(hiveX - r, hiveY - r, 2 * r, 2 * r);
        for (Bug bug : this.bugs) {
            bug.paint(g);
        }
    }

    public void step() {
        synchronized(this) {
            for (Bug bug : this.bugs) {
                bug.updateLocation();
            }
            for (Bug bug : this.bugs) {
                bug.setNeighborhood(this.swarmModel.getNeighborhood(swarmID, bug.getX(), bug.getY()));
                bug.step();
            }
        }
    }

}