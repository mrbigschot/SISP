package environment;

// Java imports
import java.awt.Color;
import java.awt.Graphics;

// Swarm imports
import swarmintelligence.*;

public class Resource extends SIObject implements Grabbable {

    int mass;
    double fX, fY;
    SwarmModel theModel;

    public Resource(SwarmModel model) {
        theModel = model;
        mass = Globals.MASS;
        this.x = SwarmUtilities.random(10, IEnvironment.ENVIRONMENT_WIDTH - 10);
        this.y = SwarmUtilities.random(10, IEnvironment.ENVIRONMENT_HEIGHT - 10);
        setOriginalX(x);
        setOriginalY(y);
    }

    public Resource(SwarmModel model, int x, int y) {
        this(model);
        this.x = x;
        this.y = y;
        setOriginalX(x);
        setOriginalY(y);
    }

    public void reset(boolean lockPosition) {
        mass = Globals.MASS;
        if (!lockPosition) {
            this.x = SwarmUtilities.random(10, IEnvironment.ENVIRONMENT_WIDTH - 10);
            this.y = SwarmUtilities.random(10, IEnvironment.ENVIRONMENT_HEIGHT - 10);
        } else {
            this.x = getOriginalX();
            this.y = getOriginalY();
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
        int drawX = (int)x - rad;
        int drawY = (int)y - rad;
        g.fillOval(drawX, drawY, 2 * rad, 2 * rad);
    }

    public double getDepletion() {
        return ((double)mass) / Globals.MASS;
    }
    
    public boolean isDepleted() {
        return mass == 0;
    }

    public int gather() {
        if (!isDepleted()) {
            mass--;
            return 1;
        }
        return 0;
    }

    @Override
    public void applyForce(double xf, double yf) {
        fX += xf;
        fY += yf;
    }

    @Override
    public void step() {
        x += fX;
        y += fY;
        fX = 0;
        fY = 0;
        theModel.emitPheromone((int)x, (int)y, (int)(getDepletion() * Globals.MAX_SMELL), Pheromone.CHANNEL_RESOURCE_A);
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