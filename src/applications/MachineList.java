package applications;

import utilities.MyInputStream;
import exceptions.MyInputException;

public class MachineList {

	private static Machine[] machineArray;
	private static int numberOfMachines;
	private static int firstMachine;
	private static Job theJob;
	
    public static void createMachineArray(){
        machineArray = new Machine[numberOfMachines];
        for (int i = 0; i < numberOfMachines; i++)
            machineArray[i] = new Machine(i);
    }
    
    static void getChangeOverInput(MyInputStream input){
        System.out.println("Enter change-over times for machines");
        for (int j = 0; j < numberOfMachines; j++) {
            int ct = input.readInteger();
            if (ct < 0)
                throw new MyInputException(MachineShopSimulator.CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0);
            machineArray[j].setChangeTime(ct);
        }
    }
    
    static void getJobsInput(MyInputStream keyboard){
        int jobID;
        for (int i = 0; i < MachineShopSimulator.getNumberOfJobs(); i++) {
        	jobID = i + 1;
            System.out.println("Enter number of tasks for job " + jobID);
            int tasks = keyboard.readInteger(); // number of tasks
            firstMachine = 0; // machine for first task
            if (tasks < 1){
                throw new MyInputException(MachineShopSimulator.EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK);
            }
            // create the job
            createJob(keyboard, jobID, tasks);
        }
    }
    
    static void createJob(MyInputStream keyboard, int jobID, int tasks){
        theJob = new Job(jobID);
        System.out.println("Enter the tasks (machine, time)"
                + " in process order");
        for (int j = 0; j < tasks; j++) {// get tasks for job i
            int theMachine = keyboard.readInteger() - 1;
            int theTaskTime = keyboard.readInteger();
            if (theMachine < 0 || theMachine > numberOfMachines
                    || theTaskTime < 1)
                throw new MyInputException(MachineShopSimulator.BAD_MACHINE_NUMBER_OR_TASK_TIME);
            if (j == 0)
                firstMachine = theMachine; // job's first machine
            theJob.addTask(theMachine, theTaskTime); // add to
        } // task queue
        machineArray[firstMachine].getJobs().put(theJob);
    }
        
    public static Machine getMachine(int index){
    	return machineArray[index];
    }
    
    public static int getNumberOfMachines(){
    	return numberOfMachines;
    }
    
    public static void setNumberOfMachines(int numMachines){
    	numberOfMachines = numMachines;
    }
    
    /** @return machine for next event */
    public static int nextEventMachine() {
        // find first machine to finish, this is the machine with smallest finish time
        int p = 0;
        int t = machineArray[0].nextEventTime();
        for (int i = 1; i < machineArray.length - 1; i++)
            if (machineArray[i].nextEventTime() < t) {// i finishes earlier
                p = i;
                t = machineArray[i].nextEventTime();
            }
        return p;
    }
}
