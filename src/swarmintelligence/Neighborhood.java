package swarmintelligence;

public class Neighborhood {

    int[][] sight, smell;
    int cX, cY;
    boolean goal, swarm;
    double gX, gY, sX, sY;
    Goal g;

    public Neighborhood(int x, int y) {
        cX = x;
        cY = y;
        goal = false;
    }

    public void setSight(int[][] s) {
        sight = s;
    }

    public void setSmell(int[][] s) {
        smell = s;
    }
    
    public void setGoal(Goal g) {
        this.g = g;
    }

    public int[][] getSight() {
        return sight;
    }

    public int[][] getSmell() {
        return smell;
    }
    
    public Goal getGoal() {
        return g;
    }

    public int see(int x, int y) {
        return sight[x][y];
    }

    public int smell(int x, int y) {
        return smell[x][y];
    }

    public void setCenter(int x, int y) {
        cX = x;
        cY = y;
    }

    public void setSwarm(int x, int y) {
        sX = x;
        sY = y;
    }

    public void setGoalPos(double x, double y) {
        gX = x;
        gY = y;
    }

    public int[] getCenter() {
        int[] returnMe = new int[2];
        returnMe[0] = cX;
        returnMe[1] = cY;
        return returnMe;
    }

    public int getHeight() {
        return sight[0].length;
    }

    public int getWidth() {
        return sight.length;
    }

    public boolean containsGoal() {
        return goal;
    }

    public boolean containsHive(int id) {
        return swarm;
    }

    @Override
    public String toString() {
        String returnMe = "Neighborhood centered at: (" + cX + ", " + cY + ")\n";
        for (int y = 0; y < sight[0].length; y++) {
            for (int x = 0; x < sight.length; x++) {
                returnMe += sight[x][y];
            }
            returnMe += "\n";
        }
        return returnMe;
    }

}
