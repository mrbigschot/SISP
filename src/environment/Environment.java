package environment;

// Java imports
import java.awt.Color;
import java.awt.Graphics;

// Swarm imports
import bugs.Bug;
import bugs.Swarm;
import pheromone.Pheromone;
import pheromone.PheromoneChannel;
import pheromone.PheromoneLayer;
import swarmintelligence.SwarmModel;
import swarmintelligence.SwarmUtilities;
import gui.ViewMode;

public class Environment implements IEnvironment {

    private final EnvironmentSettings settings;

    private int[][] grid;
    private WallLayer walls;
    private PheromoneLayer[] pheromoneChannels;
    private Resources resources;

    private final SwarmModel swarmModel;
    private Swarm swarmA, swarmB;

    public Environment(SwarmModel m) {
        this.swarmModel = m;
        this.grid = new int[ENVIRONMENT_WIDTH][ENVIRONMENT_HEIGHT];
        this.settings = new EnvironmentSettings();
        initPheromoneLayers();
        initWalls();
        initResources();
    }

    public EnvironmentSettings getSettings() { return this.settings; }

    private void initPheromoneLayers() {
        this.pheromoneChannels = new PheromoneLayer[PheromoneChannel.getSize()];
        for (PheromoneChannel channel : PheromoneChannel.values()) {
            this.pheromoneChannels[channel.ordinal()] = new PheromoneLayer(ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT);
        }
    }

    private void initWalls() {
        this.walls = new WallLayer(ENVIRONMENT_WIDTH, ENVIRONMENT_HEIGHT, this.settings.getWallCount());
    }

    private void initResources() {
        this.resources = new Resources();
        for (int ii = 0; ii < this.settings.getResourceCount(); ii++) {
            Resource resourceToAdd = createResource();
            if (resourceToAdd != null) this.resources.add(resourceToAdd);
        }
    }

    private Resource createResource() {
        int x = SwarmUtilities.randomBetween(10, ENVIRONMENT_WIDTH - 10);
        int y = SwarmUtilities.randomBetween(10, ENVIRONMENT_HEIGHT - 10);
        while (isWallAt(x, y)) {
            x = SwarmUtilities.randomBetween(10, ENVIRONMENT_WIDTH - 10);
            y = SwarmUtilities.randomBetween(10, ENVIRONMENT_HEIGHT - 10);
        }
        return createResourceAt(x, y);
    }
    private Resource createResourceAt(int x, int y) {
        if (isWallAt(x, y)) {
            return null;
        } else {
            return new Resource(swarmModel, x, y);
        }
    }

    public void addResourceAt(int x, int y) {
        Resource resourceToAdd = createResourceAt(x, y);
        if (resourceToAdd != null) {
            this.resources.add(resourceToAdd);
            this.settings.setResourceCount(this.settings.getResourceCount() + 1);
        }
    }

    public int totalResourceMassAvailable() {
        return this.resources.size() * IResource.MAX_MASS;
    }

    public void setSwarms(Swarm a, Swarm b) {
        this.swarmA = a;
        this.swarmB = b;
    }

    public boolean isWallAt(int x, int y) {
        return this.walls.isWallAt(x, y);
    }

    public PheromoneLayer getPheromoneLayer(PheromoneChannel channel) { return this.pheromoneChannels[channel.ordinal()]; }
    public int getPheromoneLevelAt(PheromoneChannel channel, int x, int y) { return getPheromoneLayer(channel).getPheromoneLevelAt(x, y); }

    public Resource findResourceWithin(int xMin, int xMax, int yMin, int yMax) {
        for (Resource resource : resources) {
            if (!resource.isDepleted()) {
                int x = (int) resource.getX();
                int y = (int) resource.getY();
                if (SwarmUtilities.isWithinRange(x, y, xMin, yMin, xMax, yMax)) {
                    return resource;
                }
            }
        }
        return null;
    }

    public Neighborhood getNeighborhood(int swarmID, double dx, double dy) {
        int ix = (int)dx; // cast dx to int for calculation
        int iy = (int)dy; // cast dy to int for calculation
        int n = Bug.SIGHT_RANGE;
        int xMin = Math.max(ix - n, 0);
        int xMax = Math.min(ix + n, ENVIRONMENT_WIDTH);
        int yMin = Math.max(iy - n, 0);
        int yMax = Math.min(iy + n, ENVIRONMENT_HEIGHT);
        int centerX = ix - xMin;
        int centerY = iy - yMin;

        Neighborhood result = new Neighborhood(centerX, centerY);
        int[][] nSight = new int[xMax - xMin][yMax - yMin];
        int[][][] nSmell = new int[PheromoneChannel.getSize()][xMax - xMin][yMax - yMin];
        for (int ii = xMin; ii < xMax; ii++) {
            for (int jj = yMin; jj < yMax; jj++) {
                if (isWallAt(ii, jj)) {
                    nSight[ii - xMin][jj - yMin] = ENV_WALL;
                } else if (grid[ii][jj] != ENV_EMPTY) {
                    nSight[ii - xMin][jj - yMin] = grid[ii][jj];
                }
                for (PheromoneChannel channel : PheromoneChannel.values()) {
                    nSmell[channel.ordinal()][ii - xMin][jj - yMin] = getPheromoneLevelAt(channel, ii, jj);
                }
            }
        }
        
        Swarm swarm = getSwarm(swarmID);
        if (containsSwarm(swarm, xMin, xMax, yMin, yMax)) {
            if (swarm != null) {
                nSight[swarm.getX() - xMin][swarm.getY() - yMin] = swarm.getSwarmID();
                result.setHive(swarm.getX(), swarm.getY());
            }
        }

        Resource foundResource = findResourceWithin(xMin, xMax, yMin, yMax);
        if (foundResource != null) {
            int resourceX = (int)foundResource.getX() - xMin;
            int resourceY = (int)foundResource.getY() - yMin;
            nSight[resourceX][resourceY] = ENV_RESOURCE; // resource overwrites everything
            result.setResource(foundResource);
        }
        result.setSight(nSight);
        result.setSmell(nSmell);
        return result;
    }

    public void setValue(int x, int y, int value) { this.grid[x][y] = value; }

    public void emitPheromone(int x, int y, int value, PheromoneChannel channel) {
        getPheromoneLayer(channel).emitPheromone(x, y, value);
    }

    public void dilute() {
        dilute(PheromoneChannel.RESOURCE_A);
        dilute(PheromoneChannel.HIVE_A);
        dilute(PheromoneChannel.HIVE_B);
    }
    public void dilute(PheromoneChannel channel) {
        int persistence = this.settings.getPheromonePersistence(channel);
        getPheromoneLayer(channel).dilute(this, persistence);
    }
    public void clean() {
        this.grid = new int[ENVIRONMENT_WIDTH][ENVIRONMENT_HEIGHT]; // (x, y)
        dilute();
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
    
    public boolean containsSwarm(Swarm swarm, int xMin, int xMax, int yMin, int yMax) {
        if (swarm != null) {
            int x = swarm.getX();
            int y = swarm.getY();
            return SwarmUtilities.isWithinRange(x, y, xMin, yMin, xMax, yMax);
        }
        return false;
    }

    public void restart(boolean lockResources, boolean lockWalls) {
        if (lockResources) {
            for (Resource resource : resources) { resource.reset(lockResources); }
        } else {
            initResources();
        }
        if (!lockWalls) initWalls();
        initPheromoneLayers();
    }
    
    public void paint(Graphics g, ViewMode viewMode) {
        switch (viewMode) {
            case NORMAL:
                g.setColor(Color.BLACK);
                for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
                    for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
                        if (isWallAt(x, y)) {
                            g.fillRect(x, y, 1, 1);
                        }
                    }
                }
                resources.paint(g);
                break;
            case PHEROMONE1:
                paintPheromoneChannel(PheromoneChannel.RESOURCE_A, g);
                break;
            case PHEROMONE2:
                paintPheromoneChannel(PheromoneChannel.HIVE_A, g);
                break;
            case PHEROMONE3:
                paintPheromoneChannel(PheromoneChannel.HIVE_B, g);
                break;
        }
    }

    private void paintPheromoneChannel(PheromoneChannel channel, Graphics graphics) {
        for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
            for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
                if (getPheromoneLayer(channel).hasPheromoneAt(x, y)) {
                    graphics.setColor(Pheromone.getColor(channel, getPheromoneLevelAt(channel, x, y)));
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
        StringBuilder returnMe = new StringBuilder();
        for (int y = 0; y < ENVIRONMENT_HEIGHT; y++) {
            for (int x = 0; x < ENVIRONMENT_WIDTH; x++) {
                returnMe.append(grid[x][y]);
            }
            returnMe.append("\n");
        }
        return returnMe.toString();
    }

    public void step() {
        for (Resource resource : resources) {
            resource.step();
        }
    }

}