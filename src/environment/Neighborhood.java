package environment;

public class Neighborhood {

    private int[][] sight;
    private int[][][] smell;
    private int cX, cY;
    private boolean hasResource, hasHive;
    private double resourceX, resourceY;
    private double hiveX, hiveY;
    private Resource resource;

    public Neighborhood(int x, int y) {
        cX = x;
        cY = y;
        hasResource = false;
    }

    public void setSight(int[][] s) {
        sight = s;
    }
    public int[][] getSight() {
        return sight;
    }
    public int see(int x, int y) {
        return sight[x][y];
    }

    public void setSmell(int[][][] s) {
        smell = s;
    }
    public int[][][] getSmell() {
        return smell;
    }
    public int smell(int channel, int x, int y) {
        return smell[channel][x][y];
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
    public int getHeight() {
        return sight[0].length;
    }
    public int getWidth() {
        return sight.length;
    }

    public boolean containsResource() {
        return hasResource;
    }
    public void setResource(Resource rsrc) {
        this.resource = rsrc;
        this.resourceX = rsrc.getX();
        this.resourceY = rsrc.getY();
        this.hasResource = true;
    }
    public Resource getResource() {
        return this.resource;
    }
    public double getResourceX() { return resourceX; }
    public double getResourceY() { return resourceY; }

    public boolean containsHive(int id) {
        return hasHive;
    }
    public void setHive(int x, int y) {
        this.hiveX = x;
        this.hiveY = y;
        this.hasHive = true;
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