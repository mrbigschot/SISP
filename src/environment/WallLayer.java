package environment;

import swarmintelligence.SwarmUtilities;

public class WallLayer {

    private final int padding = 5;

    private final boolean[][] walls;
    private final int width;
    private final int height;
    private final int density;

    public WallLayer(int width, int height, int density) {
        this.width = width;
        this.height = height;
        this.density = density;

        this.walls = new boolean[this.width][this.height];
        initBorder();
        initInnerWalls();
    }

    private void initBorder() {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.padding; x++) {
                walls[x][y] = true;
                walls[this.width - 1 - x][y] = true;
            }
        }
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.padding; y++) {
                walls[x][y] = true;
                walls[x][this.height - 1 - y] = true;
            }
        }
    }

    private void initInnerWalls() {
        for (int ii = 0; ii < this.density; ii++) {
            int x = SwarmUtilities.random(this.padding, this.width - this.padding);
            int y = SwarmUtilities.random(this.padding, this.height - this.padding);
            int l = SwarmUtilities.random(25, 200);
            int max;
            if (SwarmUtilities.coinFlip(.5)) { // horizontal
                max = Math.min(x + l, this.width);
                for (int ix = x; ix < max; ix++) {
                    walls[ix][y] = true;
                    walls[ix][y + 1] = true;
                    walls[ix][y + 2] = true;
                    walls[ix][y + 3] = true;
                    walls[ix][y + 4] = true;
                }
            } else { // vertical
                max = Math.min(y + l, this.height);
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

    public boolean isWallAt(int x, int y) {
        return this.walls[x][y];
    }
}
