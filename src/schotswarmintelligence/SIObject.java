/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

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
