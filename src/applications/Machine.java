package applications;

import dataStructures.LinkedQueue;

public class Machine {

    // data members
    private LinkedQueue jobs; // queue of waiting jobs for this machine
    private int id; 
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numberOfTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine

    // constructor
    public Machine(int idNum) {
    	id = idNum;
        jobs = new LinkedQueue();
    }
    
    /**
     * change the state of theMachine
     * @return last job run on this machine
     */
    public Job changeState() {// Task on theMachine has finished,
    	                                    // schedule next one.
    	Job lastJob;
    	if (activeJob == null) {// in idle or change-over state
    		lastJob = null;
    		// wait over, ready for new job
    		if (jobs.isEmpty()) // no waiting job
    			MachineShopSimulator.getEventList().setFinishTime(id,
    					Integer.MAX_VALUE);
    		else {// take job off the queue and work on it
    			activeJob = (Job) jobs.remove();
    			totalWait += MachineShopSimulator.getCurrentTime()
    					- activeJob.getArrivalTime();
    			numberOfTasks++;
    			MachineShopSimulator.getEventList().setFinishTime(id,
    					MachineShopSimulator.getCurrentTime() + activeJob.removeNextTask());
    		}
    	} else {// task has just finished on machine[theMachine]
    		    // schedule change-over time
    		lastJob = activeJob;
    		activeJob = null;
    		MachineShopSimulator.getEventList().setFinishTime(id,
    				MachineShopSimulator.getCurrentTime() + changeTime);
    	}

    	return lastJob;
    }
    
    public LinkedQueue getJobs() {
    	return jobs;
    }
    
    public int getTotalWait() {
    	return totalWait;
    }
    
    public int getNumberOfTasks() {
    	return numberOfTasks;
    }
    
    public void setChangeTime(int time) {
    	changeTime = time;
    }
}