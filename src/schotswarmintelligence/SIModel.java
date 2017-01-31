package schotswarmintelligence;

import java.awt.Color;
import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {

    boolean DEBUG = true;

    public void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

    
    
    @Override
    public void step() {
        
    }
    
    public void paint(Graphics g) {
         g.setColor(Color.BLUE);
        g.fillOval(100,100,100,100);
    }

}
