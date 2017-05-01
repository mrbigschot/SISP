/*
 Copyright © 2016 by Paul K. Schot
 All rights reserved.
 */
package schotswarmintelligence;

import java.awt.Graphics;
import java.util.ArrayList;

public class GoalList extends ArrayList<Goal> {

    public void paint(Graphics g) {
        for (Goal goal : this) {
            goal.paint(g);
        }
    }
    
}
