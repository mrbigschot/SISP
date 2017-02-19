/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class SIGrid {

    int[][] grid;
    boolean[][] walls;

    public SIGrid() {
        grid = new int[Globals.WIDTH][Globals.HEIGHT]; // (x, y)

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

    public int[][] getNeighborhood(double dx, double dy) {
        int ix = (int) dx;
        int iy = (int) dy;
        int n = Globals.BUG_SIGHT;
        int xmin = Math.max(ix - n, 0);
        int xmax = Math.min(ix + n, Globals.WIDTH);
        int ymin = Math.max(iy - n, 0);
        int ymax = Math.min(iy + n, Globals.HEIGHT);
        int[][] returnMe = new int[xmax - xmin][ymax - ymin];
        for (int i = xmin; i < xmax; i++) {
            for (int j = ymin; j < ymax; j++) {
                if (walls[i][j]) {
                    returnMe[i - xmin][j - ymin] = 1;
                }
                returnMe[i - xmin][j - ymin] = grid[i][j];
            }
        }
        return returnMe;
    }

    public void setValue(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        for (int x = 0; x < Globals.WIDTH; x++) {
            for (int y = 0; y < Globals.HEIGHT; y++) {
                if (grid[x][y] == 1) {
                    g.drawRect(x, y, 1, 1);
                }
            }
        }
    }

    @Override
    public String toString() {
        String returnMe = "";
        for (int i = 0; i < Globals.HEIGHT; i++) {
            for (int j = 0; j < Globals.WIDTH; j++) {
                returnMe += grid[j][i];
            }
            returnMe += "\n";
        }

        return returnMe;
    }

}
