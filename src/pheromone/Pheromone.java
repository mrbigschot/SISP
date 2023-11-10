package pheromone;

// Java imports
import java.awt.Color;

public class Pheromone implements IPheromone {

    boolean DEBUG = true;
    public void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    public static Color getColor(PheromoneChannel channel, int intensity) {
        float h = 0.0f;
        switch (channel) {
            case RESOURCE_A:
                h = 0.33f; // green
                break;
            case HIVE_A:
                h = 0.66f; // blue
                break;
            case HIVE_B:
                h = 0.0f; // red    
                break;
        }
        float s = (float)(-Math.pow(0.9975, -10 * (intensity - (MAX_VALUE + 10))) + 1);
        float b = (float)((double)intensity / MAX_VALUE);
        return Color.getHSBColor(h,s,b);
    }
    
}