package environment;

import pheromone.PheromoneChannel;

public class Neighborhood {

    private int[][] sight;
    private int[][][] smell;
    private final int centerX, centerY;
    private boolean hasResource;
    private double resourceX, resourceY;
    private boolean hasHive;
    private double hiveX, hiveY;
    private Resource resource;

    public Neighborhood(int x, int y) {
        this.centerX = x;
        this.centerY = y;
        this.hasResource = false;
        this.hasHive = false;
    }

    public void setSight(int[][] s) { this.sight = s; }
    public int see(int x, int y) { return this.sight[x][y]; }

    public void setSmell(int[][][] s) { this.smell = s; }
    public int smell(PheromoneChannel channel, int x, int y) { return this.smell[channel.ordinal()][x][y]; }

    public int[] getCenter() {
        int[] result = new int[2];
        result[0] = this.centerX;
        result[1] = this.centerY;
        return result;
    }
    public int getHeight() { return this.sight[0].length; }
    public int getWidth() { return this.sight.length; }

    public boolean containsResource() { return hasResource && !resource.isDepleted(); }
    public void setResource(Resource resource) {
        this.resource = resource;
        this.resourceX = resource.getX();
        this.resourceY = resource.getY();
        this.hasResource = true;
    }
    public Resource getResource() { return this.resource; }
    public double getResourceX() { return resourceX; }
    public double getResourceY() { return resourceY; }

    public void setHive(int x, int y) {
        this.hiveX = x;
        this.hiveY = y;
        this.hasHive = true;
    }
    
    @Override
    public String toString() {
        StringBuilder returnMe = new StringBuilder("Neighborhood centered at: (" + centerX + ", " + centerY + ")\n");
        for (int y = 0; y < sight[0].length; y++) {
            for (int x = 0; x < sight.length; x++) {
                returnMe.append(sight[x][y]);
            }
            returnMe.append("\n");
        }
        return returnMe.toString();
    }

}