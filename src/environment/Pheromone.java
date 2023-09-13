package environment;

// Java imports
import java.awt.Color;

// Swarm imports
import swarmintelligence.Globals;

public class Pheromone {

    boolean DEBUG = true;

    public void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }
    
    public static final int CHANNEL_RESOURCE_A = 0;
    public static final int CHANNEL_HIVE_A = 1;
    public static final int CHANNEL_RESOURCE_B = 2;
    public static final int CHANNEL_HIVE_B = 3;

    public static Color getColor(int channel, int intensity) {
        float h = 0.0f;
        switch (channel) {
            case CHANNEL_RESOURCE_A:
                h = 0.33f; // green
                break;
            case CHANNEL_HIVE_A:
                h = 0.66f; // blue
                break;
            case CHANNEL_HIVE_B:  
                h = 0.0f; // red    
                break;
        }
        float s = (float)(-Math.pow(0.9975, -10 * (intensity - (Globals.MAX_SMELL + 10))) + 1);
        float b = (float)((double)intensity / Globals.MAX_SMELL);
        return Color.getHSBColor(h,s,b);
    }
    
}