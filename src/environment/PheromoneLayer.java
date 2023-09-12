package environment;

// Swarm imports
import swarmintelligence.Globals;

public class PheromoneLayer {

    private int[][] pheromones;
    private final int width;
    private final int height;

    public PheromoneLayer(int width, int height) {
        this.width = width;
        this.height = height;
        this.pheromones = new int[this.width][this.height];
    }

    public int getPheromoneLevelAt(int x, int y) {
        return this.pheromones[x][y];
    }
    public boolean hasPheromoneAt(int x, int y) {
        return this.getPheromoneLevelAt(x, y) > 0;
    }
    public void setPheromoneLevelAt(int x, int y, int value) {
        this.pheromones[x][y] = value;
    }
    public void emitPheromone(int x, int y, int value) {
        this.setPheromoneLevelAt(x, y, Math.max(value, this.getPheromoneLevelAt(x, y)));
    }
    public void dilute(Environment environment) {
        int[][] newPheromones = new int[this.width][this.height];
        for (int ii = 2; ii < this.width - 2; ii++) {
            for (int jj = 2; jj < this.height - 2; jj++) {
                if (this.hasPheromoneAt(ii, jj)) {
                    int n = (int)(getPheromoneLevelAt(ii, jj) * Globals.DECAY_RATE);
                    addPheromone(ii, jj, n, newPheromones, environment);
                    addPheromone(ii - 1, jj, n, newPheromones, environment);
                    addPheromone(ii + 1, jj, n, newPheromones, environment);
                    addPheromone(ii, jj - 1, n, newPheromones, environment);
                    addPheromone(ii, jj + 1, n, newPheromones, environment);
                }
            }
        }
        this.pheromones = newPheromones;
    }

    private static void addPheromone(int x, int y, int value, int[][] pheromones, Environment environment) {
        if (!environment.isWallAt(x, y)) pheromones[x][y] = Math.min(pheromones[x][y] + value, Globals.MAX_SMELL);
    }

}
