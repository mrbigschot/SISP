package swarmintelligence;

// Java imports
import java.awt.Graphics;

public abstract class SIObject {
    
    protected double x, y;
    protected double originalX, originalY;
    
    public abstract void paint(Graphics g);

    public final void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public final double getX() {
        return this.x;
    }
    public final double getY() {
        return this.y;
    }
    public final int getIntX() { return (int)this.x; }
    public final int getIntY() { return (int)this.y; }

    public final void setOrigin(double x, double y) {
        this.originalX = x;
        this.originalY = y;
    }
    public final double getOriginalX() { return this.originalX; }
    public final double getOriginalY() { return this.originalY; }
    
    public abstract void step();
}