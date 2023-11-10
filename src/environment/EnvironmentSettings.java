package environment;

import pheromone.PheromoneChannel;

public class EnvironmentSettings {

    private final int[] pheromonePersistence;
    private int resourceCount;
    private int wallCount;

    public EnvironmentSettings() {
        this.pheromonePersistence = new int[PheromoneChannel.getSize()];
        setPheromonePersistence(PheromoneChannel.RESOURCE_A, 25);
        setPheromonePersistence(PheromoneChannel.HIVE_A, 25);
        setPheromonePersistence(PheromoneChannel.RESOURCE_B, 25);
        setPheromonePersistence(PheromoneChannel.HIVE_B, 25);

        this.resourceCount = 1;
        this.wallCount = 0;
    }

    public final void setPheromonePersistence(PheromoneChannel channel, int value) { this.pheromonePersistence[channel.ordinal()] = value; }
    public final int getPheromonePersistence(PheromoneChannel channel) { return this.pheromonePersistence[channel.ordinal()]; }

    public final void setResourceCount(int value) { this.resourceCount = value; }
    public final int getResourceCount() { return this.resourceCount; }

    public final void setWallCount(int value) { this.wallCount = value; }
    public final int getWallCount() { return this.wallCount; }

}
