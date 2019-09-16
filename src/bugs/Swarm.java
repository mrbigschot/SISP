package bugs;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import swarmintelligence.Globals;
import environment.SIGrid;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.Enumeration;
import swarmintelligence.SIModel;

public class Swarm extends ArrayList<Bug> {

    public static final int SWARM_A = 8;
    public static final int SWARM_B = 7;
    
    SIModel theModel;
    private int hX, hY;
    private final Color color;
    private int total;
    private final int swarmID;
    private SwarmConfiguration config;

    public Swarm(SIModel m, int id) {
        theModel = m;
        total = 0;
        swarmID = id;
        if (swarmID == SWARM_A) {
            color = Color.BLUE;
        } else {
            color = Color.RED;
        }
    }

    public void configure(SwarmConfiguration val) {
        this.config = val;
    }
    
    public void configure(Enumeration<BugConfiguration> val) {
        this.config = new SwarmConfiguration();
        while (val.hasMoreElements()) {
            this.config.add(val.nextElement());
        }
    }
    
    public SwarmConfiguration getConfiguration() { 
        return this.config;
    }
    
    public Color getColor() {
        return color;
    }

    public int getTotal() {
        return total;
    }

    public int getX() {
        return hX;
    }

    public int getY() {
        return hY;
    }

    public int getSwarmID() {
        return swarmID;
    }
    
    public void init(SIGrid g, boolean lockPosition) {
        total = 0;
        this.clear();
        if (!lockPosition) {
            do {
                hX = Globals.random(100, Globals.WIDTH - 100);
                hY = Globals.random(100, Globals.HEIGHT - 100);
            } while (g.isWall(hX, hY));
        }
        spawn();
    }
    
    public void reset() {
        total = 0;
        this.clear();
        spawn();
    }

    public void spawn() {
        if (config != null) {
            for (BugConfiguration bc : config) {
                for (int i = 0; i < bc.getSize(); i++) {
                    Bug bug = new Bug(this, hX, hY, color);
                    bug.setAngle(Globals.random(0, 2 * Math.PI));
                    bug.setSpeed(1);
                    bug.configure(bc);
                    this.add(bug);
                }
            }
        }
    }
    
    public void spawnBug() {
        if (config != null) {
            BugConfiguration bc = config.get(0);
            Bug bug = new Bug(this, hX, hY, color);
            bug.setAngle(Globals.random(0, 2 * Math.PI));
            bug.setSpeed(1);
            bug.configure(bc);
            this.add(bug);
        }
    }

    public void deposit(int x) {
        total += x;
    }

    public void updateLocation(int x, int y) {
        theModel.updateLocation(x, y, 2);
    }

    public void emitPheremone(int x, int y, int val, int channel) {
        theModel.emitPheremone(x, y, val, channel);
    }

    public int getPheremoneChannel() {
        if (this.swarmID == SWARM_A) {
            return 2;
        } else if (this.swarmID == SWARM_B) {
            return 3;
        }
        return 0;
    }
    
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(color);
        g2.drawOval(hX - 5, hY - 5, 10, 10);
        for (Bug bug : this) {
            bug.paint(g);
        }
    }

    public void step() {
        synchronized(this) {
            for (Bug bug : this) {
                bug.updateLocation();
            }
            for (Bug bug : this) {
                bug.setNeighborhood(theModel.getNeighborhood(swarmID, bug.getX(), bug.getY(), bug.getPheremoneInputChannel()));
                bug.step();
            }
        }
    }

}