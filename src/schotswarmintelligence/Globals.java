
package schotswarmintelligence;

public class Globals {

    public static boolean DEBUG = false;
    
    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    public static int BUG_SIGHT = 25;
    
    public static int random(int min, int max) {
        double d = Math.random();
        double range = (max - min) * d;
        return (int) (range + min);
    }
    
}
