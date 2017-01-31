package mvc;

public class Controller extends Thread {

    boolean running, stepping = false;

    int delay = 10;
 
    Viewable theView;
    Modelable theModel;

    public Controller() {
        
    }
    
    public Controller(Viewable v, Modelable m) {
        theView = v;
        theModel = m;
    }

    @Override
    public void run() {
        for (;;) {
            if (running || stepping) {
                step();
                stepping = false;  // only take one step per button press
            }
        }
    }

    private void delay() {
        try {
            Thread.sleep(delay);
        } catch (Exception ex) {
        }
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
    
    public void toggleRunning() {
        running = !running;
    }
    
    public void toggleStepping() {
        stepping = !stepping;
    }

    private void step() {
        theModel.step();
        theView.display();
    }

    Modelable getModel() {
        return theModel;
    }

    boolean getRunning() {
        return running;
    }

    public void outputDelay() {
        System.out.println("Controller delay: " + delay);
    }

}
