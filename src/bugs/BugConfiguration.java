/*
 Copyright © 2019 by Paul K. Schot
 All rights reserved.
 */
package bugs;

public class BugConfiguration {

    private double weightAvoid = 0.0;
    private double weightCondense = 0.0;
    private double weightMatch = 0.0;
    private boolean canGrab = false;
    private int size = 0;
    
    public BugConfiguration() {
        
    }
    
    public BugConfiguration(String data) {
        String[] values = data.split(";");
        this.weightAvoid = Double.parseDouble(values[0]);
        this.weightCondense = Double.parseDouble(values[1]);
        this.weightMatch = Double.parseDouble(values[2]);
        this.canGrab = (Integer.parseInt(values[3]) == 1);
        this.size = Integer.parseInt(values[4]);
    }
    
    public double getWeightAvoid() { return this.weightAvoid; }
    public int getWeightAvoidInt() { return (int)(this.weightAvoid * 100); }
    public void setWeightAvoid(double value) { this.weightAvoid = value; }
    public double getWeightCondense() { return this.weightCondense; }
    public int getWeightCondenseInt() { return (int)(this.weightCondense * 100); }
    public void setWeightCondense(double value) { this.weightCondense = value; }
    public double getWeightMatch() { return this.weightMatch; }
    public int getWeightMatchInt() { return (int)(this.weightMatch * 100); }
    public void setWeightMatch(double value) { this.weightMatch = value; }
    public boolean getCanGrab() { return this.canGrab; }
    public void setCanGrab(boolean value) { this.canGrab = value; }
    public int getSize() { return (int)(this.size); }
    public void setSize(int value) { this.size = value; }
    
    public void configureBug(Bug bug) {
        bug.setWeights(this.weightAvoid, this.weightMatch, this.weightCondense);
    }
    
    @Override
    public String toString() {
        String returnMe = "";
        returnMe += "Avoid: " + getWeightAvoid() + ", ";
        returnMe += "Match: " + getWeightMatch() + ", ";
        returnMe += "Condense: " + getWeightCondense() + ", ";
        returnMe += "Grab: " + getCanGrab() + ", ";
        returnMe += "Size: " + getSize();
        return returnMe;
    }
    
}
