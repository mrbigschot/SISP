package swarmintelligence;

import java.awt.Graphics;

public abstract class SIObject {
    
    protected double x, y;
    private double oX, oY;
    
    public abstract void paint(Graphics g);
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getOX() { return oX; }
    public double getOY() { return oY; }
    public void setOX(double val) { oX = val; }
    public void setOY(double val) { oY = val; }
    
    public void step() { 
        // to be overridden by extending classes
    }
}
