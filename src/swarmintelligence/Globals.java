package swarmintelligence;

public class Globals {

    public static boolean DEBUG = false;
    public static boolean CONTROL = false;
    public static boolean PHEREMODE1 = false;
    public static boolean PHEREMODE2 = false;
    public static boolean PHEREMODE3 = false;
    public static boolean CONTEST = false;

    public static int WIDTH = 800;
    public static int HEIGHT = 500;
    public static double MAX_SPEED = 1.5;
    public static int MASS = 1000;
    
    public static int NUM_GOALS = 1;
    public static boolean MANUAL_RESOURCES = false;

    public static final int NUM_CHANNELS = 4;
    public static int NUM_WALLS = 0;
    public static int BUG_SIGHT = 25;
    public static int BUG_SMELL = 25;
    
    public static int MAX_SMELL = 1000;
    public static double DECAY_RATE = 0.200;
    public static int PHER_1_PERSISTENCE = 25;
    public static int PHER_2_PERSISTENCE = 25;
    public static int PHER_3_PERSISTENCE = 25;

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

    public static boolean coinFlip(double chance) {
        return Math.random() < chance;
    }

    public static void toggleContest() {
        CONTEST = !CONTEST;
        updateParams();
    }
    
    public static void updateParams() {
//        double[] data = loadParams();
//        if (CONTEST) {
//            setParams(false, data);
//        } else {
//            setParams(true, data);
//        }
    }
    
    public static int totalMass() {
        return NUM_GOALS * MASS;
    }
    
    public static int[] toColor(int n) {
        int r = 0, g = 0, b = 0;
        int scaled = n / 2; 
        if (scaled < 256) {
            g = scaled;
        } else {
            scaled = scaled - 256;
            g = 255;
            b = scaled;
        }
        int[] res = {r, g, b};
        return res;
    }

}