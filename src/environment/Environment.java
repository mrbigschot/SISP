package environment;

import bugs.Swarm;
import java.awt.Color;
import java.awt.Graphics;
import swarmintelligence.Globals;
import swarmintelligence.SIModel;

public class Environment implements IEnvironment {

    int step = 0;
    int[][] grid;
    int[] pherSteps;
    int[][][] pherChannels;
    
    boolean[][] walls;
    Resource resource;
    ResourceList resources;
    Swarm swarmA, swarmB;
    SIModel theModel;

    public Environment(SIModel m) {
        theModel = m;
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        this.pherSteps = new int[Globals.NUM_CHANNELS];
        this.pherChannels = new int[Globals.NUM_CHANNELS][Globals.WIDTH][Globals.HEIGHT]; // (channel, x, y)
        initWalls();
        initResources();
    }
    
    public void restart(boolean lockResources, boolean lockWalls) {
        if (!lockResources) {
            initResources();
        } else {
            for (Resource rsrc : resources) {
                rsrc.reset(lockResources);
            }
        }
        if (!lockWalls) {
            initWalls();
        }
        this.pherChannels = new int[Globals.NUM_CHANNELS][Globals.WIDTH][Globals.HEIGHT]; // (channel, x, y)
    }
    

    private void initResources() {
        resources = new ResourceList();
        for (int i = 0; i < Globals.NUM_GOALS; i++) {
            resources.add(Environment.this.initResource());
        }
    }
    
    private Resource initResource() {
        int x = Globals.random(10, Globals.WIDTH - 10);
        int y = Globals.random(10, Globals.HEIGHT - 10);
        while (walls[x][y]) {
            x = Globals.random(10, Globals.WIDTH - 10);
            y = Globals.random(10, Globals.HEIGHT - 10);
        }
        return initResource(x, y);
    }
    
    private Resource initResource(int x, int y) {
        if (isWall(x, y)) {
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

    public boolean isWall(int x, int y) {
        return walls[x][y];
    }

    private void initWalls() {
        walls = new boolean[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        for (int i = 0; i < Globals.HEIGHT; i++) {
            for (int j = 0; j < 5; j++) {
                walls[j][i] = true;
                walls[Globals.WIDTH - 1 - j][i] = true;
            }
        }
        for (int i = 0; i < Globals.WIDTH; i++) {
            for (int j = 0; j < 5; j++) {
                walls[i][j] = true;
                walls[i][Globals.HEIGHT - 1 - j] = true;
            }
        }
        for (int i = 0; i < Globals.NUM_WALLS; i++) {
            int x = Globals.random(5, Globals.WIDTH - 5);
            int y = Globals.random(5, Globals.HEIGHT - 5);
            int l = Globals.random(25, 200);
            int max;
            if (Globals.coinFlip(.5)) {
                // horizontal
                max = Math.min(x + l, Globals.WIDTH);
                for (int ix = x; ix < max; ix++) {
                    walls[ix][y] = true;
                    walls[ix][y + 1] = true;
                    walls[ix][y + 2] = true;
                    walls[ix][y + 3] = true;
                    walls[ix][y + 4] = true;
                }
            } else {
                // vertical
                max = Math.min(y + l, Globals.HEIGHT);
                for (int iy = y; iy < max; iy++) {
                    walls[x][iy] = true;
                    walls[x + 1][iy] = true;
                    walls[x + 2][iy] = true;
                    walls[x + 3][iy] = true;
                    walls[x + 4][iy] = true;
                }
            }
        }
    }

    public Neighborhood getNeighborhood(int swarmID, double dx, double dy, int pherChannelResource) {
        int ix = (int)dx; // cast dx to int for calculation
        int iy = (int)dy; // cast dy to int for calculation
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, Globals.WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, Globals.HEIGHT);
        int centerX = ix - xmin;
        int centerY = iy - ymin;

        Neighborhood returnMe = new Neighborhood(centerX, centerY);
        int[][] nSight = new int[xmax - xmin][ymax - ymin];
        int[][][] nSmell = new int[Globals.NUM_CHANNELS][xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (isWall(i, j)) {
                    nSight[i - xmin][j - ymin] = ENV_WALL;
                } else if (grid[i][j] != ENV_EMPTY) {
                    nSight[i - xmin][j - ymin] = grid[i][j];
                }
                for (int k = 0; k < Globals.NUM_CHANNELS; k++) {
                    nSmell[k][i - xmin][j - ymin] = pherChannels[k][i][j];
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

    public void emitPheremone(int x, int y, int value, int channel) {
        pherChannels[channel][x][y] = Math.max(value, pherChannels[channel][x][y]);
    }

    private int[][] addPher(int[][] pheremoneChannel, int x, int y, int value) {
        if (!walls[x][y]) {
            pheremoneChannel[x][y] = Math.min(pheremoneChannel[x][y] + value, Globals.MAX_SMELL);
        }
        return pheremoneChannel;
    }

    public void dilute() {
        dilute(Pheremone.CHANNEL_RESOURCE_A, Globals.PHER_1_PERSISTENCE);
        dilute(Pheremone.CHANNEL_HIVE_A, Globals.PHER_2_PERSISTENCE);
        dilute(Pheremone.CHANNEL_HIVE_B, Globals.PHER_3_PERSISTENCE);
    }

    public void dilute(int channel, int persistence) {
        if (persistence != 0) {
            int[][] newPher;
            pherSteps[channel]++;
            if (pherSteps[channel] == persistence) {
                pherSteps[channel] = 0;
                newPher = new int[Globals.WIDTH][Globals.HEIGHT];
                for (int i = 2; i < Globals.WIDTH - 2; i++) {
                    for (int j = 2; j < Globals.HEIGHT - 2; j++) {
                        if (pherChannels[channel][i][j] != 0) {
                            int n = (int)(pherChannels[channel][i][j] * Globals.DECAY_RATE);
                            addPher(newPher, i, j, n);
                            addPher(newPher, i - 1, j, n);
                            addPher(newPher, i + 1, j, n);
                            addPher(newPher, i, j - 1, n);
                            addPher(newPher, i, j + 1, n);
                        }
                    }
                }
                pherChannels[channel] = newPher;
            }
        }
    }
    
    public void clean() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
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
        if (swarmID == Swarm.SWARM_A) {
            swarm = swarmA;
        } else if (swarmID == Swarm.SWARM_B) {
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
            for (int x = 0; x < Globals.WIDTH; x++) {
                for (int y = 0; y < Globals.HEIGHT; y++) {
                    if (isWall(x, y)) {
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
            resources.paint(g);
        }
    }

    private void paintPherChannel(int channel, Graphics graphics) {
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (pherChannels[channel][x][y] > 0) {
                    graphics.setColor(Pheremone.getColor(channel, pherChannels[channel][x][y]));
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
        for (int y = 0; y < Globals.HEIGHT; y++) {
            for (int x = 0; x < Globals.WIDTH; x++) {
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