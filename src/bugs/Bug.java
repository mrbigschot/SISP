package bugs;

// Java imports
import java.awt.Color;
import java.awt.Graphics;

// Swarm imports
import environment.Environment;
import pheromone.PheromoneChannel;
import swarmintelligence.Grabbable;
import environment.IEnvironment;
import environment.Neighborhood;
import pheromone.Pheromone;
import swarmintelligence.SIObject;
import swarmintelligence.SwarmUtilities;

public class Bug extends SIObject implements IBug {

    private Swarm swarm;
    private Color color;
    private PheromoneChannel channelHive = PheromoneChannel.HIVE_A;
    private PheromoneChannel channelResource = PheromoneChannel.RESOURCE_A;
    private boolean controlMode = false;

    private double wAvoid = 0.0;
    private double wCondense = 0.0;
    private double wMatch = 0.0;
    private boolean grabber = true;

    private int activeDelay = 0;
    
    private double speed;
    private double orientation; // radians, 0 is along +x axis, PI/2 is along +y axis
    private double addSpeed;
    private double addAngle;
    private final int[] pheromoneOut;
    private int hiveX, hiveY;
    private int carry = 0;

    private boolean turnLeft;

    private int seekingType = SEEKING_RESOURCE;
    private int timeSinceResource = -1;
    private int timeSinceHive = 0;
    private final int smellThreshold;
    private final int adventurous;

    private final int[][] pheromoneMemory;
    private double resourceX, resourceY;

    private int wallCounter;
    private int smellDelay;

    private Neighborhood currentNeighborhood, previousNeighborhood;
    private Grabbable grabbed;

    public Bug() {
        this.pheromoneMemory = new int[PheromoneChannel.getSize()][3];
        this.pheromoneOut = new int[PheromoneChannel.getSize()];
        this.smellThreshold = SwarmUtilities.randomBetween(50, 250);
        this.adventurous = SwarmUtilities.randomBetween(50, Pheromone.MAX_VALUE);
    }

    public Bug(Swarm s, double x, double y, Color c, int b, boolean controlMode) {
        this();
        this.activeDelay = b;
        this.swarm = s;
        this.color = c;
        this.x = x;
        this.y = y;
        this.hiveX = getIntX();
        this.hiveY = getIntY();
        if (swarm.getSwarmID() == Environment.ENV_SWARM_A) {
            channelHive = PheromoneChannel.HIVE_A;
            channelResource = PheromoneChannel.RESOURCE_A;
        } else {
            channelHive = PheromoneChannel.HIVE_B;
            channelResource = PheromoneChannel.RESOURCE_B;
        }
        this.controlMode = controlMode;
    }

    public boolean isSeekingResource() {
        return this.seekingType == SEEKING_RESOURCE;
    }
    public boolean isSeekingHive() {
        return this.seekingType == SEEKING_HIVE;
    }
    public boolean isSeekingDeposit() {
        return this.seekingType == SEEKING_DEPOSIT;
    }

    @Override
    public void step() {
        if (this.activeDelay == 0) {
            if (timeSinceResource > -1 && timeSinceResource < Pheromone.MAX_VALUE) timeSinceResource++;
            if (timeSinceHive > -1 && timeSinceHive < Pheromone.MAX_VALUE) timeSinceHive++;
            assessEnvironment();
            if (this.isSeekingResource()) {
                seekResource();            
            } else if (this.isSeekingDeposit()) {
                seekDeposit();
            } else if (this.isSeekingHive()) {
                seekHive();
            }
            calcPher();
            emitPheromone();
            move();
        } else {
            this.activeDelay--;
        }
    }
    
    // actively searching for resource
    private void seekResource() {
        if (currentNeighborhood.containsResource()) {
            towardGoal();
        } else {
            update();
        }
    }

    // returning to hive, but will be distracted by resources
    private void seekHive() {
        if (currentNeighborhood.containsResource()) {
            this.seekingType = SEEKING_RESOURCE;
            towardGoal();
        } else if (nearLocation(hiveX - x, hiveY - y)) {
            deposit();
            this.seekingType = SEEKING_RESOURCE;
            timeSinceHive = 0;
            addAngle = Math.PI;
        } else {
            update();
        }
    }

    // found resource, returning to hive to deposit
    private void seekDeposit() {
        if (nearLocation(hiveX - x, hiveY - y)) {
            deposit();
            this.seekingType = SEEKING_RESOURCE;
            timeSinceHive = 0;
            addAngle = Math.PI;
        } else {
            update();
        }
    }
    
    private void deposit() {
        swarm.deposit(carry);
        carry = 0;
    }
    
    private void defaultStep() {
        addSpeed = 0;
        addAngle = 0;
        if (speed < 1) {
            addSpeed += .1;
        }
        if (!this.controlMode) {
            avoidCollision();
            matchVel();
            condense();
        }
    }

    public void update() {
        if (this.isSeekingResource()) {
            if (hasSmelled(this.channelResource)) {
                followPheromone(this.channelResource);
            } else if (this.timeSinceHive >= this.adventurous) {
                this.seekingType = SEEKING_HIVE;
            } else if (hasSmelled(this.channelHive)) {
                avoidPheromone(this.channelHive);
            } else {
                defaultStep();
            }
        } else if (this.isSeekingDeposit()) {
            if (hasSmelled(this.channelHive)) {
                followPheromone(this.channelHive);
            } else {
                defaultStep();
            }
        } else if (this.isSeekingHive()) {
            if (hasSmelled(this.channelResource)) {
                followPheromone(this.channelResource);
            } else if (hasSmelled(this.channelHive)) {
                followPheromone(this.channelHive);
            } else {
                defaultStep();
            }
        }
    }

    public void followPheromone(PheromoneChannel channel) {
        if (smellDelay == 0) {
            smellDelay = PHEROMONE_REACTION_SPEED;
            int[] smelliest = upGradient(channel);
            addAngle = matchAngle(Math.atan2((double)smelliest[1], (double)smelliest[0]));
            speed = 1 - (Math.abs(addAngle) / Math.PI);
        } else if (smellDelay > 0) {
            smellDelay--;
        }
    }
    
    public void avoidPheromone(PheromoneChannel channel) {
        if (smellDelay == 0) {
            smellDelay = PHEROMONE_REACTION_SPEED;
            int[] smelliest = downGradient(channel);
            addAngle = matchAngle(Math.atan2((double)smelliest[1], (double)smelliest[0]));
            speed = 1 - (Math.abs(addAngle) / Math.PI);
        } else if (smellDelay > 0) {
            smellDelay--;
        }
    }
    
    private boolean hasSmelled(PheromoneChannel channel) {
        if (this.controlMode) return false;

        for (int ii = 0; ii < pheromoneMemory[channel.ordinal()].length; ii++) {
            if (pheromoneMemory[channel.ordinal()][ii] > smellThreshold) {
                return true;
            }
        }
        return false;
    }

    // checks for highest pheromone value in immediate vicinity
    private int[] upGradient(PheromoneChannel channel) {
        int cX = currentNeighborhood.getCenter()[0];
        int cY = currentNeighborhood.getCenter()[1];
        int targetX = cX + 1;
        int targetY = cY;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (currentNeighborhood.smell(channel, cX + i, cY + j) > currentNeighborhood.smell(channel, targetX, targetY)) {
                        targetX = cX + i;
                        targetY = cY + j;
                    }
                }
            }
        }
        int[] returnMe = {targetX - cX, targetY - cY};
        return returnMe;
    }
    
    // check for lowest pheremone value in immediate vicinity
    private int[] downGradient(PheromoneChannel channel) {
        int cX = currentNeighborhood.getCenter()[0];
        int cY = currentNeighborhood.getCenter()[1];
        int targetX = cX + 1;
        int targetY = cY;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if (currentNeighborhood.smell(channel, cX + i, cY + j) < currentNeighborhood.smell(channel, targetX, targetY)) {
                        targetX = cX + i;
                        targetY = cY + j;
                    }
                }
            }
        }
        int[] returnMe = {targetX - cX, targetY - cY};
        return returnMe;
    }
    
    private void calcPher() {
        if (timeSinceResource > -1) {
            pheromoneOut[this.channelResource.ordinal()] = Math.max(Pheromone.MAX_VALUE - timeSinceResource, 0);
        } else {
            pheromoneOut[this.channelResource.ordinal()] = 0;
        }
        if (timeSinceHive > -1) {
            pheromoneOut[this.channelHive.ordinal()] = Math.max(Pheromone.MAX_VALUE - timeSinceHive, 0);
        } else {
            pheromoneOut[this.channelHive.ordinal()] = 0;
        }
    }

    private void assessEnvironment() {
        if (currentNeighborhood.containsResource()) {
            resourceX = currentNeighborhood.getResourceX();
            resourceY = currentNeighborhood.getResourceY();
        }
        for (int channel = 0; channel < PheromoneChannel.getSize(); channel++) {
            for (int ii = pheromoneMemory[channel].length - 1; ii > 0; ii--) {
                pheromoneMemory[channel][ii] = pheromoneMemory[channel][ii - 1];
            }
        }
        int centerX = currentNeighborhood.getCenter()[0];
        int centerY = currentNeighborhood.getCenter()[1];
        pheromoneMemory[this.channelResource.ordinal()][0] = currentNeighborhood.smell(this.channelResource, centerX, centerY);
        pheromoneMemory[this.channelHive.ordinal()][0] = currentNeighborhood.smell(this.channelHive, centerX, centerY);
    }

    public void setNeighborhood(Neighborhood n) {
        this.previousNeighborhood = this.currentNeighborhood;
        this.currentNeighborhood = n;
        if (previousNeighborhood == null) {
            previousNeighborhood = currentNeighborhood;
        }
    }

    public void updateLocation() {
        swarm.updateLocation((int) x, (int) y);
    }

    public boolean nearLocation(double dx, double dy) {
        return (Math.abs(dx) < 8) && (Math.abs(dy) < 8);
    }
    
    private void towardGoal() {
        if (nearLocation(resourceX - x, resourceY - y)) {
            addToAngle(Math.PI);
            if (currentNeighborhood.containsResource()) {
                carry = currentNeighborhood.getResource().gather();
                timeSinceResource = 0;
            }
            if (carry > 0) this.seekingType = SEEKING_DEPOSIT;

            if (grabber) grabbed = currentNeighborhood.getResource();
        } else {
            if (speed < 1) addSpeed = .1;

            double angle = Math.atan2(resourceY - y, resourceX - x);
            addAngle = matchAngle(angle);
        }
    }

    private void avoidCollision() {
        double rAngle = 0;
        double[] com = centerOfMass(currentNeighborhood);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = (Math.atan2(com[1], com[0])) + Math.PI;
            if (angle < 0) {
                angle = (angle % (2 * Math.PI)) + (2 * Math.PI);
            } else {
                angle = angle % (2 * Math.PI);
            }
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
        double[] comC = relativeCenterOfMass(currentNeighborhood);
        double[] comP = relativeCenterOfMass(previousNeighborhood);
        double dx = comC[0] - comP[0];
        double dy = comC[1] - comP[1];
        double mag = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        double angle = Math.atan2(dy, dx);
        double[] returnMe = new double[4];
        returnMe[0] = dx;
        returnMe[1] = dy;
        returnMe[2] = mag;
        returnMe[3] = angle;
        return returnMe;
    }

    private void condense() {
        double rAngle = 0;
        double[] com = relativeCenterOfMass(currentNeighborhood);
        if (!((com[0] == 0) && (com[1] == 0))) {
            double angle = Math.atan2(com[1], com[0]);
            rAngle = matchAngle(angle);
        }
        addAngle += wCondense * rAngle;
    }

    // relativeCenterOfMass() returns the center of mass relative to this Bug's position
    private double[] relativeCenterOfMass(Neighborhood grid) {
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

    private double[] centerOfMass(Neighborhood grid) {
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
        if (SwarmUtilities.coinFlip(.1)) { // 10% of the time add some noise
            if (SwarmUtilities.coinFlip(.5)) {
                returnMe += .0005;
            } else {
                returnMe -= .0005;
            }
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
                turnLeft = SwarmUtilities.coinFlip(.5);
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

    private void emitPheromone() {
        if (pheromoneOut[this.channelHive.ordinal()] > 0) {
            swarm.emitPheromone(getIntX(), getIntY(), pheromoneOut[this.channelHive.ordinal()], this.channelHive);
        }
        if (pheromoneOut[this.channelResource.ordinal()] > 0) {
            swarm.emitPheromone(getIntX(), getIntY(), pheromoneOut[this.channelResource.ordinal()], this.channelResource);
        }
    }

    private boolean facingWall(double nx, double ny) {
        double dx = nx - x;
        double dy = ny - y;
        int gx = (int)(currentNeighborhood.getCenter()[0] + dx);
        int gy = (int)(currentNeighborhood.getCenter()[1] + dy);
        return currentNeighborhood.see(gx, gy) == IEnvironment.ENV_WALL;
    }

    public void setWeights(double wA, double wM, double wC) {
        wAvoid = wA;
        wMatch = wM;
        wCondense = wC;
    }
    public void setCanGrab(boolean value) {
        this.grabber = value;
    }

    public void setSpeed(double s) {
        this.speed = s;
    }

    public void accel(double d) {
        if ((speed + d < 0)) {
            speed = 0;
        } else {
            if (speed + d > MAX_SPEED) {
                speed = MAX_SPEED;
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
        
        int size = (carry > 0) ? BUG_SIZE_LARGE : BUG_SIZE;
        int drawX = getIntX() - size / 2;
        int drawY = getIntY() - size / 2;
        g.fillOval(drawX, drawY, size, size);
    }

}
