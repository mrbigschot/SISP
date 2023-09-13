package swarmintelligence;

public class SwarmUtilities {

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

    public static boolean isWithinRange(int x, int y, int xMin, int yMin, int xMax, int yMax) {
        return ((xMin <= x) && (xMax > x)) && ((yMin <= y) && (yMax > y));
    }

    public static double randomAngle() {
        return random(0, 2 * Math.PI);
    }

}
