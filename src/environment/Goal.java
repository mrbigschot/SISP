package environment;

import java.awt.Color;
import java.awt.Graphics;
import swarmintelligence.Globals;
import swarmintelligence.Grabbable;
import swarmintelligence.SIModel;
import swarmintelligence.SIObject;

public class Goal extends SIObject implements Grabbable {

    int mass;
    double fX, fY;
    SIModel theModel;

    public Goal(SIModel model) {
        theModel = model;
        mass = Globals.MASS;
        this.x = Globals.random(10, Globals.WIDTH - 10);
        this.y = Globals.random(10, Globals.HEIGHT - 10);
        setOX(x);
        setOY(y);
    }

    public Goal(SIModel model, int x, int y) {
        this(model);
        this.x = x;
        this.y = y;
        setOX(x);
        setOY(y);
    }

    public void reset(boolean lockPosition) {
        mass = Globals.MASS;
        if (!lockPosition) {
            this.x = Globals.random(10, Globals.WIDTH - 10);
            this.y = Globals.random(10, Globals.HEIGHT - 10);
        } else {
            this.x = getOX();
            this.y = getOY();
        }
    }

    @Override
    public void paint(Graphics g) {
        if (mass == 0) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(0, 150, 0));
        }
        int rad = (int) (getDepletion() * 10);
        g.fillOval((int) x - rad, (int) y - rad, 2 * rad, 2 * rad);
    }

    public double getDepletion() {
        return ((double)mass) / Globals.MASS;
    }
    
    public boolean isDepleted() {
        return mass == 0;
    }

    public int gather() {
        if (mass > 0) {
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
        theModel.emitPheremone((int)x, (int)y, (int)(getDepletion() * Globals.MAX_SMELL), 1);
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