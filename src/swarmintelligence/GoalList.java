package swarmintelligence;

import java.awt.Graphics;
import java.util.ArrayList;

public class GoalList extends ArrayList<Goal> {

    public void paint(Graphics g) {
        for (Goal goal : this) {
            goal.paint(g);
        }
    }
    
}
