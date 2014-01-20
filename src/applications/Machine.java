package applications;

import dataStructures.LinkedQueue;

public class Machine {

    // data members
    private LinkedQueue jobQ; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobQ = new LinkedQueue();
    }
    
    public LinkedQueue getJobs() {
    	return jobQ;
    }
    
    public int getChangeTime() {
    	return changeTime;
    }
    
    public int getTotalWait() {
    	return totalWait;
    }
    
    public int getNumberOfTasks() {
    	return numTasks;
    }
    
    public Job getActiveJob() {
    	return activeJob;
    }
    
    public void setChangeTime(int time) {
    	changeTime = time;
    }
    
    public void setTotalWait(int delay) {
    	totalWait = delay;
    }
    
    public void setNumberOfTasks() {
    	numTasks++;
    }
    
    public void setActiveJob(Job job) {
    	activeJob = job;
    }
}