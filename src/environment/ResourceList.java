package environment;

import java.awt.Graphics;
import java.util.ArrayList;

public class ResourceList extends ArrayList<Resource> {

    public void paint(Graphics g) {
        for (Resource goal : this) {
            goal.paint(g);
        }
    }
    
}
