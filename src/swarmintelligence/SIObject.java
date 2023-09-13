package swarmintelligence;

// Java imports
import java.awt.Graphics;

public abstract class SIObject {
    
    protected double x, y;
    private double originalX, originalY;
    
    public abstract void paint(Graphics g);
    
    public final double getX() {
        return this.x;
    }
    public final double getY() {
        return this.y;
    }
    
    public final double getOriginalX() { return this.originalX; }
    public final double getOriginalY() { return this.originalY; }
    public final void setOriginalX(double val) { this.originalX = val; }
    public final void setOriginalY(double val) { this.originalY = val; }
    
    public void step() { 
        // to be overridden by extending classes
    }
}
