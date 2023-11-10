package pheromone;

public enum PheromoneChannel {
    RESOURCE_A,
    HIVE_A,
    RESOURCE_B,
    HIVE_B;

    public static int getSize() { return PheromoneChannel.values().length; }
}