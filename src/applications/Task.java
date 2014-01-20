package applications;

public class Task {
    // data members
    private int machine;
    private int time;

    // constructor
    public Task(int theMachine, int theTime) {
        machine = theMachine;
        time = theTime;
    }
    
    public int getMachine() {
    	return machine;
    }
    
    public int getTime() {
    	return time;
    }
}