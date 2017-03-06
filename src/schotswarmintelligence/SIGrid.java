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

    public SIGrid() {
        theGoal = new Goal();
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        pher = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
        initWalls();
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
    }

    public Neighborhood getNeighborhood(double dx, double dy) {
        int ix = (int) dx;
        int iy = (int) dy;
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, Globals.WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, Globals.HEIGHT);
        int[][] nGrid = new int[xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (walls[i][j]) {
                    nGrid[i - xmin][j - ymin] = 1;
                } else {
                    nGrid[i - xmin][j - ymin] = grid[i][j];
                }
            }
        }
        if (containsGoal(xmin, xmax, ymin, ymax)) {
            try {
                nGrid[(int) theGoal.getX() - xmin][(int) theGoal.getY() - ymin] = 9;
            } catch (Exception e) {

            }
        }
        return new Neighborhood(nGrid, ix - xmin, iy - ymin);
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void clean() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)
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
                    g.drawRect(x, y, 1, 1);
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
