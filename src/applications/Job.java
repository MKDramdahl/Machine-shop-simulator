package applications;

import dataStructures.LinkedQueue;

public class Job {

    // data members
    private LinkedQueue tasks; // this job's tasks
    private int totalTime; // sum of scheduled task times
    private int arrivalTime; // arrival time at current queue
    private int id; // job identifier

    // constructor
    public Job(int theId) {
        id = theId;
        tasks = new LinkedQueue();
        // length and arrivalTime have default value 0
    }

    // other methods
    public void addTask(int theMachine, int theTime) {
        tasks.put(new Task(theMachine, theTime));
    }

    /**
     * remove next task of job and return its time also update length
     */
    public int removeNextTask() {
        int theTime = ((Task) tasks.remove()).getTime();
        totalTime += theTime;
        return theTime;
    }
    
    /**
     * move theJob to machine for its next task
     * @return false iff no next task
     */
    public boolean moveToNextMachine() {
        if (tasks.isEmpty()) {// no next task
            System.out.println("Job " + id + " has completed at "
                    + MachineShopSimulator.getCurrentTime() + " Total wait was "
            		+ (MachineShopSimulator.getCurrentTime() - totalTime));
            return false;
        } else {// theJob has a next task
                // get machine for next task
            int p = ((Task) tasks.getFrontElement()).getMachine();
            // put on machine p's wait queue
            MachineShopSimulator.getMachineArray()[p].getJobs().put(this);
            arrivalTime = MachineShopSimulator.getCurrentTime();
            // if p idle, schedule immediately
            if (MachineShopSimulator.getEventList().nextEventTime(p)
            		== MachineShopSimulator.getFinishBy()) {// machine is idle
                MachineShopSimulator.getMachineArray()[p].changeState(p);
            }
            return true;
        }
    }
    
    public LinkedQueue getTasks() {
    	return tasks;
    }
    
    public int getTotalTime() {
    	return totalTime;
    }
    
    public int getArrivalTime() {
    	return arrivalTime;
    }
    
    public int getID() {
    	return id;
    }
    
    public void setArrivalTime(int time) {
    	arrivalTime = time;
    }
}