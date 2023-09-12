package environment;

// Java imports
import java.awt.Color;
import java.awt.Graphics;

// Swarm imports
import bugs.Swarm;
import swarmintelligence.Globals;
import swarmintelligence.SIModel;

public class Environment implements IEnvironment {

    int step = 0;
    int[][] grid;
    int[] pherSteps;
    PheromoneLayer[] pheromoneChannels;
    private WallLayer walls;

    Resource resource;
    ResourceList resources;
    Swarm swarmA, swarmB;
    SIModel theModel;

    public Environment(SIModel m) {
        theModel = m;
        grid = new int[ENVIRONMENT_WIDTH][ENVIRONMENT_HEIGHT]; // (x, y)
        this.pherSteps = new int[Globals.NUM_CHANNELS];
        initPheromoneLayers();
        initWalls();
        initResources();
    }

    private void initPheromoneLayers() {
        this.pheromoneChannels = new PheromoneLayer[Globals.NUM_CHANNELS];
        for (int ii = 0; ii < Globals.NUM_CHANNELS; ii++) {
            this.pheromoneChannels[ii] = new PheromoneLayer(ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT);
        }
    }

    private void initWalls() {
        this.walls = new WallLayer(ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT, Globals.NUM_WALLS);
    }

    public void restart(boolean lockResources, boolean lockWalls) {
        if (lockResources) {
            for (Resource rsrc : resources) { rsrc.reset(lockResources); }
        } else {
            initResources();
        }
        if (!lockWalls) initWalls();
        initPheromoneLayers();
    }

    private void initResources() {
        resources = new ResourceList();
        for (int i = 0; i < Globals.NUM_GOALS; i++) {
            resources.add(Environment.this.initResource());
        }
    }
    
    private Resource initResource() {
        int x = Globals.random(10, ENVIRONMENT_WIDTH - 10);
        int y = Globals.random(10, ENVIRONMENT_HEIGHT - 10);
        while (isWallAt(x, y)) {
            x = Globals.random(10, ENVIRONMENT_WIDTH - 10);
            y = Globals.random(10, ENVIRONMENT_HEIGHT - 10);
        }
        return initResource(x, y);
    }
    
    private Resource initResource(int x, int y) {
        if (isWallAt(x, y)) {
            return null;
        } else {
            return new Resource(theModel, x, y);
        }
    }
    
    public void addResource(int x, int y) {
        Resource rsrc = initResource(x, y);
        if (rsrc != null) {
            resources.add(rsrc);
            Globals.NUM_GOALS++;
        }
    }

    public void setSwarms(Swarm a, Swarm b) {
        this.swarmA = a;
        this.swarmB = b;
    }

    public boolean isWallAt(int x, int y) { return this.walls.isWallAt(x, y); }
    public int getPheromoneLevelAt(int channel, int x, int y) {
        return this.pheromoneChannels[channel].getPheromoneLevelAt(x, y);
    }

    public Neighborhood getNeighborhood(int swarmID, double dx, double dy, int pherChannelResource) {
        int ix = (int)dx; // cast dx to int for calculation
        int iy = (int)dy; // cast dy to int for calculation
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, ENVIRONMENT_WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, ENVIRONMENT_HEIGHT);
        int centerX = ix - xmin;
        int centerY = iy - ymin;

        Neighborhood returnMe = new Neighborhood(centerX, centerY);
        int[][] nSight = new int[xmax - xmin][ymax - ymin];
        int[][][] nSmell = new int[Globals.NUM_CHANNELS][xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (isWallAt(i, j)) {
                    nSight[i - xmin][j - ymin] = ENV_WALL;
                } else if (grid[i][j] != ENV_EMPTY) {
                    nSight[i - xmin][j - ymin] = grid[i][j];
                }
                for (int k = 0; k < Globals.NUM_CHANNELS; k++) {
                    nSmell[k][i - xmin][j - ymin] = getPheromoneLevelAt(k, i, j);
                }
            }
        }
        
        Swarm swarm = getSwarm(swarmID);
        if (containsSwarm(swarm, xmin, xmax, ymin, ymax)) {
            if (swarm != null) {
                nSight[swarm.getX() - xmin][swarm.getY() - ymin] = swarm.getSwarmID();
                returnMe.setHive(swarm.getX(), swarm.getY());
            }
        }
        
        if (containsResource(xmin, xmax, ymin, ymax)) {
            int rsrcX = (int)resource.getX() - xmin;
            int rsrcY = (int)resource.getY() - ymin;
            nSight[rsrcX][rsrcY] = ENV_RESOURCE; // resource overwrites everything
            returnMe.setResource(resource);
        }
        returnMe.setSight(nSight);
        returnMe.setSmell(nSmell);
        return returnMe;
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void emitPheromone(int x, int y, int value, int channel) {
        this.pheromoneChannels[channel].emitPheromone(x, y, value);
    }

    public void dilute() {
        dilute(Pheremone.CHANNEL_RESOURCE_A, Globals.PHER_1_PERSISTENCE);
        dilute(Pheremone.CHANNEL_HIVE_A, Globals.PHER_2_PERSISTENCE);
        dilute(Pheremone.CHANNEL_HIVE_B, Globals.PHER_3_PERSISTENCE);
    }

    public void dilute(int channel, int persistence) {
        if (persistence != 0) {
            pherSteps[channel]++;
            if (pherSteps[channel] == persistence) {
                pherSteps[channel] = 0;
                this.pheromoneChannels[channel].dilute(this);
            }
        }
    }
    
    public void clean() {
        grid = new int[ENVIRONMENT_WIDTH][ENVIRONMENT_HEIGHT]; // (x, y)
        dilute();
    }

    public boolean containsResource(int xmin, int xmax, int ymin, int ymax) {
        for (Resource rsrc : resources) {
            if (!rsrc.isDepleted()) {
                int x = (int) rsrc.getX();
                int y = (int) rsrc.getY();
                if (withinRange(x, y, xmin, ymin, xmax, ymax)) {
                    resource = rsrc;
                    return true;
                }
            }
        }
        return false;
    }

    public Swarm getSwarm(int swarmID) {
        Swarm swarm = null;
        if (swarmID == ENV_SWARM_A) {
            swarm = swarmA;
        } else if (swarmID == ENV_SWARM_B) {
            swarm = swarmB;            
        }
        return swarm;
    }
    
    public boolean containsSwarm(Swarm swarm, int xmin, int xmax, int ymin, int ymax) {
        if (swarm != null) {
            int x = swarm.getX();
            int y = swarm.getY();
            return withinRange(x, y, xmin, ymin, xmax, ymax);
        }
        return false;
    }
    
    public boolean withinRange(int x, int y, int xmin, int ymin, int xmax, int ymax) {
        return ((xmin <= x) && (xmax > x)) && ((ymin <= y) && (ymax > y));
    }
    
    public void paint(Graphics g) {
        if (Globals.PHEREMODE1) {
            paintPherChannel(Pheremone.CHANNEL_RESOURCE_A, g);
        } else if (Globals.PHEREMODE2) {
            paintPherChannel(Pheremone.CHANNEL_HIVE_A, g);
        } else if (Globals.PHEREMODE3) {
            paintPherChannel(Pheremone.CHANNEL_HIVE_B, g);
        } else {
            g.setColor(Color.BLACK);
            for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
                for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
                    if (isWallAt(x, y)) {
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
            resources.paint(g);
        }
    }

    private void paintPherChannel(int channel, Graphics graphics) {
        for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
            for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
                if (pheromoneChannels[channel].hasPheromoneAt(x, y)) {
                    graphics.setColor(Pheremone.getColor(channel, getPheromoneLevelAt(channel, x, y)));
                    graphics.fillRect(x, y, 1, 1);
                } else {
                    graphics.setColor(Color.BLACK);
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }
    }

    @Override
    public String toString() {
        String returnMe = "";
        for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
            for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
                returnMe += grid[x][y];
            }
            returnMe += "\n";
        }
        return returnMe;
    }

    public boolean isDone() {
        return false;
    }

    public void step() {
        for (Resource rsrc : resources) {
            rsrc.step();
        }
    }

}