package swarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Goal extends SIObject implements Grabbable {

    int mass;
    double fX, fY;

    public Goal() {
        mass = Globals.MASS;
        this.x = Globals.random(10, Globals.WIDTH - 10);
        this.y = Globals.random(10, Globals.HEIGHT - 10);
    }

    public Goal(int x, int y) {
        this();
        this.x = x;
        this.y = y;
    }

    public void reset() {
        mass = Globals.MASS;
    }

    @Override
    public void paint(Graphics g) {
        if (mass == 0) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(new Color(0, 150, 0));
        }
        int rad = (int) (mass / 100.0);
        g.fillOval((int) x - rad, (int) y - rad, 2 * rad, 2 * rad);
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
