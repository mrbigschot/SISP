package swarmintelligence;

import java.awt.Graphics;

public abstract class SIObject {
    
    double x, y;
    
    public abstract void paint(Graphics g);
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
}
