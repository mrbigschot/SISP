package bugs;

public interface IBug {

    int PHEROMONE_REACTION_SPEED = 3;

    int SEEKING_RESOURCE = 1;
    int SEEKING_HIVE = 2;
    int SEEKING_DEPOSIT = 3;

    int BUG_SIZE = 4;
    int BUG_SIZE_LARGE = 6;

    double MAX_SPEED = 3.0;
    int SIGHT_RANGE = 25;
    // TODO: int SMELL_RANGE = 25;
}