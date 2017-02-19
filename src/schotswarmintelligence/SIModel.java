package schotswarmintelligence;

import java.awt.Graphics;
import mvc.Modelable;

public class SIModel implements Modelable {
    
    SIGrid theGrid;
    
    public SIModel() {
        theGrid = new SIGrid();
    }
    
    @Override
    public void step() {
        
    }
    
    public void paint(Graphics g) {
        theGrid.paint(g);
    }

}
