package swarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class SIGrid {

    int step = 0;
    int[][] grid;
    int[][] pher;
    boolean[][] walls;
    Goal theGoal;
    GoalList goals;
    Swarm swarmA, swarmB;

    public SIGrid() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        initWalls();
        createGoals();
    }

    public void restart() {
        for (Goal g : goals) {
            g.reset();
        }
        pher = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
    }

    private Goal createGoal() {
        int x = Globals.random(10, Globals.WIDTH - 10);
        int y = Globals.random(10, Globals.HEIGHT - 10);
        while (walls[x][y]) {
            x = Globals.random(10, Globals.WIDTH - 10);
            y = Globals.random(10, Globals.HEIGHT - 10);
        }
        return new Goal(x, y);
    }

    private void createGoals() {
        goals = new GoalList();
        for (int i = 0; i < Globals.NUM_GOALS; i++) {
            goals.add(createGoal());
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

    public Neighborhood getNeighborhood(int swarmID, double dx, double dy) {
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
                nSmell[i - xmin][j - ymin] = pher[i][j];
            }
        }
        if (swarmID == 8) {
            if (containsSwarmA(xmin, xmax, ymin, ymax)) {
                try {
                    nSight[swarmA.getX() - xmin][swarmA.getY() - ymin] = swarmA.swarmID;
                    returnMe.setSwarm(swarmA.getX(), swarmA.getY());
                    returnMe.swarm = true;
                } catch (Exception e) {
                    returnMe.swarm = false;
                }
            }
        } else {
            if (containsSwarmB(xmin, xmax, ymin, ymax)) {
                try {
                    nSight[swarmB.getX() - xmin][swarmB.getY() - ymin] = swarmB.swarmID;
                    returnMe.setSwarm(swarmB.getX(), swarmB.getY());
                    returnMe.swarm = true;
                } catch (Exception e) {
                    returnMe.swarm = false;
                }
            }

        }

        if (containsGoal(xmin, xmax, ymin, ymax)) {
            try {
                nSight[(int) theGoal.getX() - xmin][(int) theGoal.getY() - ymin] = 9; // goal overwrites everything
                if (!theGoal.isDepleted()) {
                    returnMe.setGoalPos(theGoal.getX(), theGoal.getY());
                    returnMe.goal = true;
                }
                returnMe.setGoal(theGoal);
            } catch (Exception e) {
                returnMe.goal = false;
            }
        }
        returnMe.setSight(nSight);
        returnMe.setSmell(nSmell);
        return returnMe;
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void setPher(int x, int y, int value) {
        pher[x][y] = value;
    }

    private int[][] addPher(int[][] g, int x, int y, int value) {
        if (!walls[x][y]) {
            g[x][y] = Math.min(g[x][y] + value, 511);
        }
        return g;
    }

    public void decay() {
        if (step == 30) {
            step = 0;
            int[][] newPher = new int[Globals.WIDTH][Globals.HEIGHT];
            for (int i = 2; i < Globals.WIDTH - 2; i++) {
                for (int j = 2; j < Globals.HEIGHT - 2; j++) {
                    if (pher[i][j] != 0) {
                        int n = (int) (pher[i][j] * .2);
                        addPher(newPher, i, j, n);
                        addPher(newPher, i - 1, j, n);
                        addPher(newPher, i + 1, j, n);
                        addPher(newPher, i, j - 1, n);
                        addPher(newPher, i, j + 1, n);
                    }
                }
            }
            pher = newPher;
        } else {
            step++;
        }
    }

    public void clean() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        decay();
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
        if (Globals.PHEREMODE) {
            paintPher(g);
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

    private void paintPher(Graphics g) {
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (pher[x][y] > 0) {
                    g.setColor(new Color(0, (int) (pher[x][y] / 2.0), 0));
//                    if (pher[x][y] < 255) {
//                        try {
//                            g.setColor(new Color(0, pher[x][y], 0));
//                        } catch (Exception e) {
//                            System.out.println("" + pher[x][y] + " is weird");
//                            throw (e);
//                        }
//                    } else {
//                        g.setColor(new Color(0, 255, 0));
//                    }
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
