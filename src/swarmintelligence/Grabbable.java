package swarmintelligence;

public interface Grabbable {
    
    public void applyForce(double dir, double f);
    
    public void step();
    
    public double getSpeed();
    
    public double getAngle();
    
}
