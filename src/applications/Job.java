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