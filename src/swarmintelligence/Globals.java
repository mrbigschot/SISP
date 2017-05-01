package swarmintelligence;

import io.MyReader;

public class Globals {

    public static boolean DEBUG = false;
    public static boolean CONTROL = false;
    public static boolean PHEREMODE = false;
    public static boolean CONTEST = false;

    public static int WIDTH = 800;
    public static int HEIGHT = 500;
    public static double MAX_SPEED = 1.5;
    public static int MASS = 1000;
    
    public static int NUM_WALLS = 0;
    public static int NUM_GOALS = 1;
    public static int BUG_SIGHT = 25;
    public static int BUG_SMELL = 25;
    public static int SWARM_SIZE = 100;

    public static double A_TOP_WEIGHT = 1;
    public static double A_MID_WEIGHT = 0;
    public static double A_LOW_WEIGHT = 0;
    public static double A_EXPLORE_RATE = .3;
    public static double A_CONDENSE_RATE = .2;

    public static double B_TOP_WEIGHT = 1;
    public static double B_MID_WEIGHT = 0;
    public static double B_LOW_WEIGHT = 0;
    public static double B_EXPLORE_RATE = .3;
    public static double B_CONDENSE_RATE = .2;
    
    public static double A_GRABBERS = 0.0;
    public static double B_GRABBERS = 0.0;

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
    

    public static String saveString(boolean a) {
        String returnMe = "";
        if (a) {
            returnMe += A_TOP_WEIGHT + ";";
            returnMe += A_MID_WEIGHT + ";";
            returnMe += A_LOW_WEIGHT + ";";
            returnMe += A_EXPLORE_RATE + ";";
            returnMe += A_CONDENSE_RATE + ";";
            returnMe += A_GRABBERS + ";";
            
        } else {
            returnMe += B_TOP_WEIGHT + ";";
            returnMe += B_MID_WEIGHT + ";";
            returnMe += B_LOW_WEIGHT + ";";
            returnMe += B_EXPLORE_RATE + ";";
            returnMe += B_CONDENSE_RATE + ";";
            returnMe += B_GRABBERS + ";";
        }
        return returnMe;
    }
    
    public static void updateParams() {
        double[] data = loadParams();
        if (CONTEST) {
            setParams(false, data);
        } else {
            setParams(true, data);
        }
    }
    
    public static double[] loadParams() {
        double[] returnMe = new double[6];
        MyReader mr = new MyReader("data");
        while (mr.hasMoreData()) {
            String line = mr.giveMeTheNextLine();
            String[] data = line.split(";");
            for (int i = 0; i < data.length; i++) {
                returnMe[i] = Double.parseDouble(data[i]);
            }
        }
        return returnMe;
    }

    public static void setParams(boolean a, double[] d) {
        if (a) {
            A_TOP_WEIGHT = d[0];
            A_MID_WEIGHT = d[1];
            A_LOW_WEIGHT = d[2];
            A_EXPLORE_RATE = d[3];
            A_CONDENSE_RATE = d[4];
            A_GRABBERS = d[5];
        } else {
            B_TOP_WEIGHT = d[0];
            B_MID_WEIGHT = d[1];
            B_LOW_WEIGHT = d[2];
            B_EXPLORE_RATE = d[3];
            B_CONDENSE_RATE = d[4];
            B_GRABBERS = d[5];
        }
    }
    
    public static int totalMass() {
        return NUM_GOALS * MASS;
    }

}
