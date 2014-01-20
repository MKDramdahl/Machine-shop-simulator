package applications;

import dataStructures.LinkedQueue;

public class Machine {

    // data members
    private LinkedQueue jobs; // queue of waiting jobs for this machine
    private int changeTime; // machine change-over time
    private int totalWait; // total delay at this machine
    private int numberOfTasks; // number of tasks processed on this machine
    private Job activeJob; // job currently active on this machine

    // constructor
    public Machine() {
        jobs = new LinkedQueue();
    }
    
    public LinkedQueue getJobs() {
    	return jobs;
    }
    
    public int getChangeTime() {
    	return changeTime;
    }
    
    public int getTotalWait() {
    	return totalWait;
    }
    
    public int getNumberOfTasks() {
    	return numberOfTasks;
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
    	numberOfTasks++;
    }
    
    public void setActiveJob(Job job) {
    	activeJob = job;
    }
}