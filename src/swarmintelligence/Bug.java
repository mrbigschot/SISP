package swarmintelligence;

import java.awt.Color;
import java.awt.Graphics;

public class Bug extends SIObject {

    Swarm theSwarm;
    int swarmID;
    Color color;
    double speed;
    double accel;
    double orientation; // radians, 0 is along +x axis, PI/2 is along +y axis
    double addSpeed;
    double addAngle;
    int pherOut = 0;
    int hX, hY;
    int carry = 0;

    double wAvoid = 1;
    double wCondense = 1;
    double wMatch = 1;

    boolean turning, turnLeft;

    boolean foundGoal = false;
    boolean seeGoal = false;
    int[] pherMem;
    boolean carrying = false;
    double goalX, goalY;

    int wallCounter;
    int smellDelay;

    Neighborhood nHoodC, nHoodP;
    Grabbable grabbed;
    boolean grabber = true;

    public Bug() {
        pherMem = new int[5];
    }

    public Bug(Swarm s, double x, double y, Color c) {
        this();
        theSwarm = s;
        this.x = x;
        this.y = y;
        this.hX = (int) x;
        this.hY = (int) y;
        color = c;
        if (s.swarmID == 8) {
            grabber = Globals.coinFlip(Globals.A_GRABBERS);
        } else {
            grabber = Globals.coinFlip(Globals.B_GRABBERS);
        }
    }

    public void step() {
        calcPher();
        check();
        if (carrying) {
            returning();
        } else {
            searching();
        }
        emitPher();
        move();
    }

    private void searching() {
        if (nHoodC.containsGoal() || foundGoal) {
            towardGoal();
        } else {
            update();
        }
    }

    private void returning() {
        if ((Math.abs(hX - x) < 5) && (Math.abs(hY - y) < 5)) {
            carrying = false;
            seeGoal = false;
            theSwarm.deposit(carry);
            carry = 0;
            addAngle = Math.PI;
        } else {
            speed = 1;
            double angle = Math.atan2(theSwarm.hY - y, theSwarm.hX - x);
            addAngle = matchAngle(angle);
            if (grabbed != null && ((Math.abs(hX - x) > 10) || (Math.abs(hY - y) > 10))) {
                double xf = Math.cos(orientation) * speed;
                double yf = Math.sin(orientation) * speed;
                grabbed.applyForce(xf, yf);
                grabbed = null;
            }
        }
    }

    private void defaultStep() {
        addSpeed = 0;
        addAngle = 0;
        if (speed < 1) {
            addSpeed += .1;
        }
        if (!Globals.CONTROL) {
            avoidCollision();
            matchVel();
            condense();
        }
    }

    public void update() {
        if (hasSmelled()) {
            if (smellDelay == 0) {
                smellDelay = 5;
                speed = .8;
                int[] smelliest = upGradient();
                addAngle = matchAngle(Math.atan2((double) smelliest[1], (double) smelliest[0]));
            } else {
                if (smellDelay > 0) {
                    smellDelay--;
                }
            }
        } else {
            defaultStep();
        }
    }

    private boolean hasSmelled() {
        if (Globals.CONTROL) {
            return false;
        }
        for (int i = 0; i < pherMem.length; i++) {
            if (pherMem[i] > 0) {
                return true;
            }
        }
        return false;

    }

    // checks 4 cardinal directions for highest pheromone value
    private int[] upGradient() {
        int cX = nHoodC.getCenter()[0];
        int cY = nHoodC.getCenter()[1];
        int mX = cX + 1;
        int mY = cY;
        for (int i = -1; i <= 1; i = i + 2) {
            if (nHoodC.smell(cX + i, cY) > nHoodC.smell(mX, mY)) {
                mX = cX + i;
                mY = cY;
            }
            if (nHoodC.smell(cX, cY + i) > nHoodC.smell(mX, mY)) {
                mX = cX;
                mY = cY + i;
            }
        }
        int[] returnMe = {mX - cX, mY - cY};
        return returnMe;
    }

    private void calcPher() {
        pherOut = 0;
        if (Globals.CONTROL) {
            return;
        }
        if (foundGoal) {
            double dx = x - goalX;
            double dy = y - goalY;
            double d = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
            pherOut = Math.max(511 - (int) d, 1);
        }
    }

    private void check() {
        seeGoal = nHoodC.containsGoal();
        if (seeGoal) {
            foundGoal = true;
            goalX = nHoodC.gX;
            goalY = nHoodC.gY;
        }
        for (int i = pherMem.length - 1; i > 0; i--) {
            pherMem[i] = pherMem[i - 1];
        }
        pherMem[0] = nHoodC.smell(nHoodC.getCenter()[0], nHoodC.getCenter()[1]);
    }

    public void setNeighborhood(Neighborhood n) {
        this.nHoodP = this.nHoodC;
        this.nHoodC = n;
        if (nHoodP == null) {
            nHoodP = nHoodC;
        }
    }

    public void updateLocation() {
        theSwarm.updateLocation((int) x, (int) y);
    }

    private void towardGoal() {
        if ((Math.abs(goalX - x) < 5) && (Math.abs(goalY - y) < 5)) {
            addToAngle(Math.PI);
            if (nHoodC.containsGoal()) {
                carry = nHoodC.getGoal().gather();
            }
            if (carry != 0) {
                carrying = true;
                seeGoal = false;
            } else {
                foundGoal = false;
            }
            if (grabber) {
                grabbed = nHoodC.getGoal();
            }
        } else {
            if (speed < 1) {
                addSpeed = .1;
            }
            double angle = Math.atan2(goalY - y, goalX - x);
            addAngle = matchAngle(angle);
        }
    }

    private void avoidCollision() {
        double rAngle = 0;
        double[] com = centerOfMass2(nHoodC);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = (Math.atan2(com[1], com[0])) + Math.PI;
            if (angle < 0) {
                angle = (angle % (2 * Math.PI)) + (2 * Math.PI);
            } else {
                angle = angle % (2 * Math.PI);
            }
//            if (Globals.DEBUG) {
//                System.out.println("B" + index);
//                System.out.println("\tPOS: (" + x + ", " + y + ")");
//                System.out.println("\tCOM:(" + com[0] + ", " + com[1] + ")");
//                System.out.println("\tθ: " + angle);
//            }
            rAngle = matchAngle(angle);
        }
        addAngle += wAvoid * rAngle;
    }

    private void matchVel() {
        double rAngle = 0;
        double rSpeed = 0;
        double[] d = calcVel();
        if (d[2] != 0) {
            double diffAngle = Math.abs(orientation - d[3]);
            if ((diffAngle == Math.PI)) {
                if (speed > 0) {
                    rSpeed = -.1;
                } else {
                    rAngle = matchAngle(d[3]);
                }
            } else {
                rAngle = matchAngle(d[3]);
                rSpeed = matchSpeed(d[2]);
            }
        }
        addAngle += wMatch * rAngle;
        addSpeed += wMatch * rSpeed;
    }

    private double[] calcVel() {
        double[] comC = centerOfMass1(nHoodC);
        double[] comP = centerOfMass1(nHoodP);
        double dx = comC[0] - comP[0];
        double dy = comC[1] - comP[1];
        double mag = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double angle = Math.atan2(dy, dx);
//        if (Globals.DEBUG) {
//            System.out.println("B" + index);
//            System.out.println("\tPOS: (" + nHoodC.cX + ", " + nHoodC.cY + ")");
//            System.out.println("\tspeed: " + speed);
//            System.out.println("\tangle: " + orientation);
//            System.out.println("COM = (" + comC[0] + ", " + comC[1] + ")");
//            System.out.println("\tdX: " + dx);
//            System.out.println("\tdY: " + dy);
//            System.out.println("\tmag = " + mag);
//            System.out.println("\tangle = " + angle);
//            System.out.println("\n");
//        }
        double[] returnMe = new double[4];
        returnMe[0] = dx;
        returnMe[1] = dy;
        returnMe[2] = mag;
        returnMe[3] = angle;
        return returnMe;
    }

    private void condense() {
        double rAngle = 0;
        double[] com = centerOfMass1(nHoodC);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = Math.atan2(com[1], com[0]);
            rAngle = matchAngle(angle);
//            if (Globals.DEBUG) {
//                System.out.println("B" + index + " condense");
//                System.out.println("\tPOS: (" + x + ", " + y + ")");
//                System.out.println("\tCOM:(" + com[0] + ", " + com[1] + ")");
//                System.out.println("\tθ: " + angle);
//                System.out.println("adjust by: " + rAngle);
//            }
        }
        addAngle += wCondense * rAngle;
    }

    // centerOfMass() returns the center of mass relative to this Bug's position
    private double[] centerOfMass1(Neighborhood grid) {
        double[] returnMe = new double[2];
        double xsum = 0;
        double ysum = 0;
        int count = 0;
        for (int ix = 0; ix < grid.getWidth(); ix++) {
            for (int iy = 0; iy < grid.getHeight(); iy++) {
                if (!(ix == grid.getCenter()[0] && iy == grid.getCenter()[1]) && (grid.see(ix, iy) == 2)) {
                    xsum += ix;
                    ysum += iy;
                    count++;
                }
            }
        }
        if (count != 0) {
            double xavg = xsum / count;
            double yavg = ysum / count;
            returnMe[0] = xavg - grid.getCenter()[0];
            returnMe[1] = yavg - grid.getCenter()[1];
        } else {
            returnMe[0] = 0;
            returnMe[1] = 0;
        }
        return returnMe;
    }

    private double[] centerOfMass2(Neighborhood grid) {
        double[] returnMe = new double[2];
        double xsum = 0;
        double ysum = 0;
        int count = 0;
        int xmin = Math.max(0, grid.getCenter()[0] - 5);
        int xmax = Math.min(grid.getWidth(), grid.getCenter()[0] + 5);
        int ymin = Math.max(0, grid.getCenter()[1] - 5);
        int ymax = Math.min(grid.getWidth(), grid.getCenter()[1] + 5);
        for (int ix = xmin; ix < xmax; ix++) {
            for (int iy = ymin; iy < ymax; iy++) {
                if (!(ix == grid.getCenter()[0] && iy == grid.getCenter()[1]) && (grid.see(ix, iy) == 2)) {
                    xsum += ix;
                    ysum += iy;
                    count++;
                }
            }
        }
        if (count != 0) {
            double xavg = xsum / count;
            double yavg = ysum / count;
            returnMe[0] = xavg - grid.getCenter()[0];
            returnMe[1] = yavg - grid.getCenter()[1];
        } else {
            returnMe[0] = 0;
            returnMe[1] = 0;
        }
        return returnMe;
    }

    private double matchAngle(double a) {
        if (a < 0) {
            a += 2 * Math.PI;
        }
        double delta = orientation - a; // -2pi to 2pi
        double returnMe;
        if (delta < Math.PI) {
            if (delta < 0) {
                if (delta < -Math.PI) {
                    returnMe = -.1;
                } else {
                    returnMe = .1;
                }
            } else {
                returnMe = -.1;
            }
        } else {
            returnMe = .1;
        }
        if (Globals.coinFlip(.1)) {
            returnMe += .05;
        }
        return returnMe;
    }

    private double matchSpeed(double s) {
        if (speed < s) {
            return .1;
        } else {
            return -.1;
        }
    }

    public void move() {
        addToAngle(addAngle);
        accel(addSpeed);
        double nx = x + (speed * Math.cos(orientation));
        double ny = y + (speed * Math.sin(orientation));
        if (facingWall(nx, ny)) {
            if (wallCounter == 0) {
                turnLeft = Globals.coinFlip(.5);
            }
            if (turnLeft) {
                addToAngle(-.5);
            } else {
                addToAngle(.5);
            }
            wallCounter = 10;
        } else {
            if (wallCounter > 0) {
                wallCounter--;
            }
            x = nx;
            y = ny;
        }
    }

    private void emitPher() {
        if (pherOut != 0) {
            theSwarm.setPher((int) x, (int) y, pherOut);
        }
    }

    private boolean facingWall(double nx, double ny) {
        double dx = nx - x;
        double dy = ny - y;
        int gx = (int) (nHoodC.getCenter()[0] + dx);
        int gy = (int) (nHoodC.getCenter()[1] + dy);
        return nHoodC.see(gx, gy) == 1;
    }

    public void setWeights(double wA, double wM, double wC) {
        wAvoid = wA;
        wMatch = wM;
        wCondense = wC;
    }

    public void setSpeed(double s) {
        this.speed = s;
    }

    public void accel(double d) {
        if ((speed + d < 0)) {
            speed = 0;
        } else {
            if (speed + d > Globals.MAX_SPEED) {
                speed = Globals.MAX_SPEED;
            } else {
                speed += d;
            }
        }
    }

    public double getSpeed() {
        return speed;
    }

    public void setAngle(double a) {
        orientation = a;
        if (orientation < 0) {
            orientation = (orientation % (2 * Math.PI)) + (2 * Math.PI);
        } else {
            orientation = orientation % (2 * Math.PI);
        }
    }

    public void addToAngle(double a) {
        orientation += a;
        if (orientation < 0) {
            orientation = (orientation % (2 * Math.PI)) + (2 * Math.PI);
        } else {
            orientation = orientation % (2 * Math.PI);
        }
    }

    public double getAngle() {
        return orientation;
    }

    @Override
    public String toString() {
        String returnMe = "Bug: (" + x + ", " + y + ")\n";

        returnMe += "angle = " + orientation + "\n";
        returnMe += "speed = " + speed;

        return returnMe;

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
//        if (pherMem[0] != 0) {
//            g.setColor(Color.ORANGE);
//        }
//        if (foundGoal) {
//            g.setColor(new Color(200, 200, 255));
//        }
        int d = 3;
        if (carry != 0) {
            d = 4;
        }
        g.fillOval((int) x - d / 2, (int) y - d / 2, d, d);
    }

}
