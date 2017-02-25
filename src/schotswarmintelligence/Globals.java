
package schotswarmintelligence;

public class Globals {

    public static boolean DEBUG = false;
    public static boolean TEST = false;
    
    public static int WIDTH = 800;
    public static int HEIGHT = 500;
    public static int BUG_SIGHT = 25;
    public static int SWARM_SIZE = 150;
    public static double MAX_SPEED = 1.5;
    
    public static double AVOID_WEIGHT = .5;
    public static double CONDENSE_WEIGHT = .2;
    public static double MATCH_WEIGHT = .3;
    
    public static int random(int min, int max) {
        double d = Math.random();
        double range = (max - min) * d;
        return (int) (range + min);
    }
    
    public static double random(double min, double max) {
        double d = Math.random();
        double range = (max - min) * d;
        return range + min;
    }
    
    public static boolean coinFlip() {
        return Math.random() < .5;
    }
    
}
