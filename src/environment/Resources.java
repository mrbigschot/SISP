package environment;

// Java imports
import java.awt.Graphics;
import java.util.ArrayList;

public class Resources extends ArrayList<Resource> {

    public void paint(Graphics g) {
        for (Resource goal : this) {
            goal.paint(g);
        }
    }
    
}
