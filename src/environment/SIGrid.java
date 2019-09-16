package environment;

import bugs.Swarm;
import java.awt.Color;
import java.awt.Graphics;
import swarmintelligence.Globals;
import swarmintelligence.SIModel;

public class SIGrid {

    int step = 0;
    int step1 = 0;
    int step2 = 0;
    int step3 = 0;
    int[][] grid;
    int[][] pher1; // resource pheremones
    int[][] pher2; // swarm A pheremones
    int[][] pher3; // swarm B pheremones
    boolean[][] walls;
    Goal theGoal;
    GoalList goals;
    Swarm swarmA, swarmB;
    SIModel theModel;

    public SIGrid(SIModel m) {
        theModel = m;
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher1 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher2 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher3 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        initWalls();
        createGoals();
    }
    
    public void restart(boolean lockResources, boolean lockWalls) {
        if (!lockResources) {
            createGoals();
        } else {
            for (Goal g : goals) {
                g.reset(lockResources);
            }
        }
        if (!lockWalls) {
            initWalls();
        }
        pher1 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher2 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher3 = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
    }

    private Goal createGoal() {
        int x = Globals.random(10, Globals.WIDTH - 10);
        int y = Globals.random(10, Globals.HEIGHT - 10);
        while (walls[x][y]) {
            x = Globals.random(10, Globals.WIDTH - 10);
            y = Globals.random(10, Globals.HEIGHT - 10);
        }
        return new Goal(theModel, x, y);
    }
    
    private Goal createGoal(int x, int y) {
        if (walls[x][y]) {
            return null;
        } else {
            return new Goal(theModel, x, y);
        }
    }

    private void createGoals() {
        goals = new GoalList();
        for (int i = 0; i < Globals.NUM_GOALS; i++) {
            goals.add(createGoal());
        }
    }
    
    public void addResource(int x, int y) {
        Goal goal = createGoal(x, y);
        if (goal != null) {
            goals.add(goal);
            Globals.NUM_GOALS++;
        }
    }

    public void setSwarmA(Swarm s) {
        swarmA = s;
    }

    public void setSwarmB(Swarm s) {
        swarmB = s;
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

    public Neighborhood getNeighborhood(int swarmID, double dx, double dy, int pherChannel) {
        int ix = (int) dx;
        int iy = (int) dy;
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, Globals.WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, Globals.HEIGHT);

        Neighborhood returnMe = new Neighborhood(ix - xmin, iy - ymin);
        int[][] nSight = new int[xmax - xmin][ymax - ymin];
        int[][] nSmell = new int[xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (walls[i][j]) {
                    nSight[i - xmin][j - ymin] = 1;
                } else {
                    if (grid[i][j] != 0) {
                        nSight[i - xmin][j - ymin] = grid[i][j];
                    }
                }
                switch (pherChannel) {
                    case 1:
                        nSmell[i - xmin][j - ymin] = pher1[i][j];
                        break;
                    case 2:
                        nSmell[i - xmin][j - ymin] = pher2[i][j];
                        break;
                    case 3:
                        nSmell[i - xmin][j - ymin] = pher3[i][j];
                        break;
                }
            }
        }
        if (swarmID == Swarm.SWARM_A) {
            if (containsSwarmA(xmin, xmax, ymin, ymax)) {
                try {
                    nSight[swarmA.getX() - xmin][swarmA.getY() - ymin] = swarmA.getSwarmID();
                    returnMe.setSwarm(swarmA.getX(), swarmA.getY());
                    returnMe.setSwarm(true);
                } catch (Exception e) {
                    returnMe.setSwarm(false);
                }
            }
        } else if (swarmID == Swarm.SWARM_B) {
            if (containsSwarmB(xmin, xmax, ymin, ymax)) {
                try {
                    nSight[swarmB.getX() - xmin][swarmB.getY() - ymin] = swarmB.getSwarmID();
                    returnMe.setSwarm(swarmB.getX(), swarmB.getY());
                } catch (Exception e) {
                    returnMe.setSwarm(false);
                }
            }
        }

        if (containsGoal(xmin, xmax, ymin, ymax)) {
            try {
                nSight[(int) theGoal.getX() - xmin][(int) theGoal.getY() - ymin] = 9; // goal overwrites everything
                if (!theGoal.isDepleted()) {
                    returnMe.setGoalPos(theGoal.getX(), theGoal.getY());
                }
                returnMe.setGoal(theGoal);
            } catch (Exception e) {
                
            }
        }
        returnMe.setSight(nSight);
        returnMe.setSmell(nSmell);
        return returnMe;
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void emitPheremone(int x, int y, int value, int channel) {
        switch (channel) {
            case 1:
                pher1[x][y] = Math.max(value, pher1[x][y]);
                break;
            case 2:
                pher2[x][y] = Math.max(value, pher2[x][y]);
                break;
            case 3:
                pher3[x][y] = Math.max(value, pher3[x][y]);
                break;
        }
    }

    private int[][] addPher(int[][] pheremoneChannel, int x, int y, int value) {
        if (!walls[x][y]) {
            pheremoneChannel[x][y] = Math.min(pheremoneChannel[x][y] + value, Globals.MAX_SMELL);
        }
        return pheremoneChannel;
    }

    public void dilute() {
        if (Globals.PHER_1_PERSISTENCE != 0) {
            step1++;
            if (step1 == Globals.PHER_1_PERSISTENCE) {
                step1 = 0;
                int[][] newPher1 = new int[Globals.WIDTH][Globals.HEIGHT];
                for (int i = 2; i < Globals.WIDTH - 2; i++) {
                    for (int j = 2; j < Globals.HEIGHT - 2; j++) {
                        if (pher1[i][j] != 0) {
                            int n = (int) (pher1[i][j] * Globals.DECAY_RATE);
                            addPher(newPher1, i, j, n);
                            addPher(newPher1, i - 1, j, n);
                            addPher(newPher1, i + 1, j, n);
                            addPher(newPher1, i, j - 1, n);
                            addPher(newPher1, i, j + 1, n);
                        }
                    }
                }
                pher1 = newPher1;
            }
        }
        if (Globals.PHER_1_PERSISTENCE != 0) { 
            step2++;
            if (step2 == Globals.PHER_2_PERSISTENCE) {
                step2 = 0;
                int[][] newPher2 = new int[Globals.WIDTH][Globals.HEIGHT];
                for (int i = 2; i < Globals.WIDTH - 2; i++) {
                    for (int j = 2; j < Globals.HEIGHT - 2; j++) {
                        if (pher2[i][j] != 0) {
                            int n = (int) (pher2[i][j] * Globals.DECAY_RATE);
                            addPher(newPher2, i, j, n);
                            addPher(newPher2, i - 1, j, n);
                            addPher(newPher2, i + 1, j, n);
                            addPher(newPher2, i, j - 1, n);
                            addPher(newPher2, i, j + 1, n);
                        }
                    }
                }
                pher2 = newPher2;
            }
        }
        if (Globals.PHER_3_PERSISTENCE != 0) { 
            step3++;
            if (step3 == Globals.PHER_3_PERSISTENCE) {
                step3 = 0;
                int[][] newPher3 = new int[Globals.WIDTH][Globals.HEIGHT];
                for (int i = 2; i < Globals.WIDTH - 2; i++) {
                    for (int j = 2; j < Globals.HEIGHT - 2; j++) {
                        if (pher3[i][j] != 0) {
                            int n = (int) (pher3[i][j] * Globals.DECAY_RATE);
                            addPher(newPher3, i, j, n);
                            addPher(newPher3, i - 1, j, n);
                            addPher(newPher3, i + 1, j, n);
                            addPher(newPher3, i, j - 1, n);
                            addPher(newPher3, i, j + 1, n);
                        }
                    }
                }
                pher3 = newPher3;
            }
        }
    }

    public void clean() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        dilute();
    }

    public boolean containsGoal(int xmin, int xmax, int ymin, int ymax) {
        for (Goal goal : goals) {
            int x = (int) goal.getX();
            int y = (int) goal.getY();
            if (((xmin <= x) && (xmax > x))
                    && ((ymin <= y) && (ymax > y))) {
                if (!goal.isDepleted()) {
                    theGoal = goal;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsSwarmA(int xmin, int xmax, int ymin, int ymax) {
        try {
            int x = swarmA.getX();
            int y = swarmA.getY();
            if (((xmin <= x) && (xmax > x)) && ((ymin <= y) && (ymax > y))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean containsSwarmB(int xmin, int xmax, int ymin, int ymax) {
        try {
            int x = swarmB.getX();
            int y = swarmB.getY();
            if (((xmin <= x) && (xmax > x)) && ((ymin <= y) && (ymax > y))) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void paint(Graphics g) {
        if (Globals.PHEREMODE1) {
            paintPher1(g);
        } else if (Globals.PHEREMODE2) {
            paintPher2(g);
        } else if (Globals.PHEREMODE3) {
            paintPher3(g);
        } else {
            g.setColor(Color.BLACK);
            for (int x = 0; x < Globals.WIDTH; x++) {
                for (int y = 0; y < Globals.HEIGHT; y++) {
                    if (walls[x][y]) {
                        g.fillRect(x, y, 1, 1);
                    }
                }
            }
            goals.paint(g);
        }
    }

    private void paintPher1(Graphics g) {
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (pher1[x][y] > 0) {
                    int[] rgb = Globals.toColor(pher1[x][y]);
                    g.setColor(new Color(rgb[0], rgb[1], rgb[2]));
                    g.fillRect(x, y, 1, 1);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }
    
    private void paintPher2(Graphics g) {
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (pher2[x][y] > 0) {
                    int[] rgb = Globals.toColor(pher2[x][y]);
                    g.setColor(new Color(rgb[2], rgb[0], rgb[1]));
                    g.fillRect(x, y, 1, 1);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
    }
    
    private void paintPher3(Graphics g) {
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (pher3[x][y] > 0) {
                    int[] rgb = Globals.toColor(pher3[x][y]);
                    g.setColor(new Color(rgb[1], rgb[2], rgb[0]));
                    g.fillRect(x, y, 1, 1);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, 1, 1);
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
        for (Goal g : goals) {
            g.step();
        }
    }

}
