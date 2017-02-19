/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

public class Neighborhood {

    int[][] grid;
    int cX, cY;

    public Neighborhood(int[][] g, int x, int y) {
        grid = g;
        cX = x;
        cY = y;
    }

    public void setGrid(int[][] g) {
        grid = g;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setCenter(int x, int y) {
        cX = x;
        cY = y;
    }

    public int[] getCenter() {
        int[] returnMe = new int[2];
        returnMe[0] = cX;
        returnMe[1] = cY;
        return returnMe;
    }

    @Override
    public String toString() {
        String returnMe = "Neighborhood centered at: (" + cX + ", " + cY + ")\n";
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                returnMe += grid[i][j];
            }
            returnMe += "\n";
        }
        return returnMe;
    }

}
