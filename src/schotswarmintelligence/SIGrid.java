/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class SIGrid {

    int[][] grid;
    int[][] pher;
    boolean[][] walls;
    Goal theGoal;

//    MyFrame f;

    public SIGrid() {
        theGoal = new Goal();
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        initWalls();
//        f = new MyFrame();
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

//        for (int i = 0; i < 20; i++) {
//            int x = Globals.random(5, Globals.WIDTH - 5);
//            int y = Globals.random(5, Globals.HEIGHT - 5);
//            int l = Globals.random(25, 200);
//            int max;
//            if (Globals.coinFlip(.5)) {
//                // horizontal
//                max = Math.min(x + l, Globals.WIDTH);
//                for (int ix = x; ix < max; ix++) {
//                    walls[ix][y] = true;
//                    walls[ix][y + 1] = true;
//                    walls[ix][y + 2] = true;
//                    walls[ix][y + 3] = true;
//                    walls[ix][y + 4] = true;
//                }
//            } else {
//                // vertical
//                max = Math.min(y + l, Globals.HEIGHT);
//                for (int iy = y; iy < max; iy++) {
//                    walls[x][iy] = true;
//                    walls[x + 1][iy] = true;
//                    walls[x + 2][iy] = true;
//                    walls[x + 3][iy] = true;
//                    walls[x + 4][iy] = true;
//                }
//            }
//            
//        }
    }

    public Neighborhood getNeighborhood(double dx, double dy) {
        int ix = (int) dx;
        int iy = (int) dy;
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, Globals.WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, Globals.HEIGHT);

        // Sight Grid
        Neighborhood returnMe = new Neighborhood(ix - xmin, iy - ymin);
        int[][] nGrid = new int[xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (walls[i][j]) {
                    nGrid[i - xmin][j - ymin] = 1;
                } else {
                    if (grid[i][j] != 0) {
                        nGrid[i - xmin][j - ymin] = grid[i][j];
                    }
                }
            }
        }
        if (containsGoal(xmin, xmax, ymin, ymax)) {
            try {
                nGrid[(int) theGoal.getX() - xmin][(int) theGoal.getY() - ymin] = 9; // goal overwrites everything
                returnMe.setGoal(theGoal.getX(), theGoal.getY());
                returnMe.goal = true;
            } catch (Exception e) {
                returnMe.goal = false;
            }
        }
        returnMe.setSight(nGrid);

        // Smell Grid
        n = Globals.BUG_SMELL;
        xmin = Math.max(ix - n, 0);
        xmax = Math.min(ix + n, Globals.WIDTH);
        ymin = Math.max(iy - n, 0);
        ymax = Math.min(iy + n, Globals.HEIGHT);
        nGrid = new int[xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                nGrid[i - xmin][j - ymin] = pher[i][j];
            }
        }
        returnMe.setSmell(nGrid);
        return returnMe;
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void setPher(int x, int y, int value) {
        int xmin = Math.max(x - 15, 0);
        int ymin = Math.max(y - 15, 0);
        int xmax = Math.min(x + 15, Globals.WIDTH);
        int ymax = Math.min(y + 15, Globals.HEIGHT);
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                pher[i][j] = value;
            }
        }
    }

    public void decay() {
        for (int i = 0; i < Globals.WIDTH; i++) {
            for (int j = 0; j < Globals.HEIGHT; j++) {
                if (pher[i][j] != 0) {
                    pher[i][j]--;
                }
            }
        }
    }

    public void clean() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        decay();
//        f.setGrid(pher);
//        f.repaint();
    }

    public boolean containsGoal(int xmin, int xmax, int ymin, int ymax) {
        try {
            int x = (int) theGoal.getX();
            int y = (int) theGoal.getY();
            return ((xmin <= x) && (xmax > x))
                    && ((ymin <= y) && (ymax > y));
        } catch (Exception e) {
            return false;
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (walls[x][y]) {
                    g.fillRect(x, y, 1, 1);
                }
            }
        }
        try {
            theGoal.paint(g);
        } catch (Exception e) {

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

}
