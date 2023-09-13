package environment;

public interface IEnvironment {
    
    int ENV_EMPTY = 0;
    int ENV_WALL = 1;
    int ENV_SWARM_A = 4;
    int ENV_SWARM_B = 5;
    int ENV_RESOURCE = 9;

    int ENVIRONMENT_WIDTH = 600;
    int ENVIRONMENT_HEIGHT = 600;
}