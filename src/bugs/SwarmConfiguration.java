package bugs;

// Java imports
import java.util.ArrayList;
import java.util.Enumeration;

// Swarm imports
import io.MyReader;

public class SwarmConfiguration extends ArrayList<BugConfiguration> {

    public SwarmConfiguration() { }
    
    public SwarmConfiguration(String filename) {
        MyReader mr = new MyReader(filename);
        while (mr.hasMoreData()) {
            this.add(new BugConfiguration(mr.giveMeTheNextLine()));
        }
    }
    
    public SwarmConfiguration(Enumeration<BugConfiguration> val) {
        while (val.hasMoreElements()) {
            this.add(val.nextElement());
        }
    }
    
//    public int getSwarmSize() { return this.swarmSize; }
    
    boolean DEBUG = true;

    public void debug(String s) {
        if (DEBUG) {
            System.out.println(s);
        }
    }

}
