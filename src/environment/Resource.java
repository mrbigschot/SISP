package environment;

// Java imports
import java.awt.Color;
import java.awt.Graphics;

// Swarm imports
import pheromone.IPheromone;
import pheromone.PheromoneChannel;
import swarmintelligence.SwarmModel;
import swarmintelligence.SwarmUtilities;
import swarmintelligence.SIObject;
import swarmintelligence.Grabbable;

public class Resource extends SIObject implements IResource, Grabbable {

    private int mass;
    private double fX, fY;
    private final SwarmModel swarmModel;

    public Resource(SwarmModel model) {
        this(model, SwarmUtilities.randomBetween(10, IEnvironment.ENVIRONMENT_WIDTH - 10), SwarmUtilities.randomBetween(10, IEnvironment.ENVIRONMENT_HEIGHT - 10));
    }
    public Resource(SwarmModel model, int x, int y) {
        this.swarmModel = model;
        this.mass = MAX_MASS;
        setPosition(x, y);
        setOrigin(x, y);
    }

    public void reset(boolean lockPosition) {
        this.mass = MAX_MASS;
        if (lockPosition) {
            super.setPosition(getOriginalX(), getOriginalY());
        } else {
            super.setPosition(SwarmUtilities.randomBetween(10, IEnvironment.ENVIRONMENT_WIDTH - 10), SwarmUtilities.randomBetween(10, IEnvironment.ENVIRONMENT_HEIGHT - 10));
        }
    }

    @Override
    public void paint(Graphics g) {
        if (isDepleted()) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(0, 150, 0));
        }
        int rad = (int)(getDepletion() * 10);
        int drawX = getIntX() - rad;
        int drawY = getIntY() - rad;
        g.fillOval(drawX, drawY, 2 * rad, 2 * rad);
    }

    public double getDepletion() {
        return ((double)this.mass) / MAX_MASS;
    }
    public boolean isDepleted() {
        return this.mass == 0;
    }
    public int gather() {
        if (isDepleted()) return 0;

        int gatheredMass = 1;
        this.mass -= gatheredMass;
        return gatheredMass;
    }

    @Override
    public void applyForce(double xf, double yf) {
        fX += xf;
        fY += yf;
    }

    @Override
    public void step() {
        super.x += fX;
        super.y += fY;
        fX = 0;
        fY = 0;
        this.swarmModel.emitPheromone(getIntX(), getIntY(), (int)(getDepletion() * IPheromone.MAX_VALUE), PheromoneChannel.RESOURCE_A);
    }

    @Override
    public double getSpeed() {
        return Math.sqrt(Math.pow(fX, 2) + Math.pow(fY, 2));
    }

    @Override
    public double getAngle() {
        return Math.atan2(fY, fX);
    }

}