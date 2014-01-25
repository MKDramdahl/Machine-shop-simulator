/** machine shop simulation */

package applications;

import utilities.MyInputStream;
import exceptions.MyInputException;

public class MachineShopSimulator {
    
    public static final String NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1 = "number of machines must be >= 1";
    public static final String NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1 = "number of machines and jobs must be >= 1";
    public static final String CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0 = "change-over time must be >= 0";
    public static final String EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK = "each job must have >= 1 task";
    public static final String BAD_MACHINE_NUMBER_OR_TASK_TIME = "bad machine number or task time";
    

    // data members of MachineShopSimulator
    private static int currentTime; // current time
    private static int numberOfMachines; // number of machines
    private static int numberOfJobs; // number of jobs
    private static EventList eventList; // pointer to event list
    private static Machine[] machineArray; // array of machines
    private static int finishBy; // all machines finish before this

    // methods
    public static int getCurrentTime() {
    	return currentTime;
    }
    
    public static EventList getEventList() {
    	return eventList;
    }
    
    public static Machine[] getMachineArray() {
    	return machineArray;
    }
    
    public static int getFinishBy() {
    	return finishBy;
    }

    /** input machine shop data */
    static void inputData() {
        // define the input stream to be the standard input stream
        MyInputStream keyboard = new MyInputStream();

        System.out.println("Enter number of machines and jobs");
        numberOfMachines = keyboard.readInteger();
        numberOfJobs = keyboard.readInteger();
        if (numberOfMachines < 1 || numberOfJobs < 1)
            throw new MyInputException(NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1);

        // create event and machine queues
        eventList = new EventList(numberOfMachines, finishBy);
        machineArray = new Machine[numberOfMachines + 1];
        for (int i = 0; i < numberOfMachines; i++)
            machineArray[i] = new Machine(i);

        // input the change-over times
        System.out.println("Enter change-over times for machines");
        for (int j = 0; j < numberOfMachines; j++) {
            int ct = keyboard.readInteger();
            if (ct < 0)
                throw new MyInputException(CHANGE_OVER_TIME_MUST_BE_AT_LEAST_0);
            machineArray[j].setChangeTime(ct);
        }

        // input the jobs
        Job theJob;
        int jobID;
        for (int i = 0; i < numberOfJobs; i++) {
        	jobID = i + 1;
            System.out.println("Enter number of tasks for job " + jobID);
            int tasks = keyboard.readInteger(); // number of tasks
            int firstMachine = 0; // machine for first task
            if (tasks < 1)
                throw new MyInputException(EACH_JOB_MUST_HAVE_AT_LEAST_1_TASK);

            // create the job
            theJob = new Job(jobID);
            System.out.println("Enter the tasks (machine, time)"
                    + " in process order");
            for (int j = 0; j < tasks; j++) {// get tasks for job i
                int theMachine = keyboard.readInteger() - 1;
                int theTaskTime = keyboard.readInteger();
                if (theMachine < 0 || theMachine > numberOfMachines
                        || theTaskTime < 1)
                    throw new MyInputException(BAD_MACHINE_NUMBER_OR_TASK_TIME);
                if (j == 0)
                    firstMachine = theMachine; // job's first machine
                theJob.addTask(theMachine, theTaskTime); // add to
            } // task queue
            machineArray[firstMachine].getJobs().put(theJob);
        }
    }

    /** load first jobs onto each machine */
    static void startShop() {
        for (int p = 0; p < numberOfMachines; p++)
            machineArray[p].changeState(p);
    }

    /** process all jobs to completion */
    static void simulate() {
        while (numberOfJobs > 0) {// at least one job left
            int nextToFinish = eventList.nextEventMachine();
            currentTime = eventList.nextEventTime(nextToFinish);
            // change job on machine nextToFinish
            Job theJob = machineArray[nextToFinish].changeState(nextToFinish);
            // move theJob to its next machine
            // decrement numJobs if theJob has finished
            if (theJob != null && !theJob.moveToNextMachine())
                numberOfJobs--;
        }
    }

    /** output wait times at machines */
    static void outputStatistics() {
        System.out.println("Finish time = " + currentTime);
        int machineID;
        for (int p = 0; p < numberOfMachines; p++) {
        	machineID = p + 1;
            System.out.println("Machine " + machineID + " completed "
                    + machineArray[p].getNumberOfTasks() + " tasks");
            System.out.println("The total wait time was "
                    + machineArray[p].getTotalWait());
            System.out.println();
        }
    }

    /** entry point for machine shop simulator */
    public static void main(String[] args) {
        finishBy = Integer.MAX_VALUE;
        /*
         * It's vital that we (re)set this to 0 because if the simulator is called
         * multiple times (as happens in the acceptance tests), because timeNow
         * is static it ends up carrying over from the last time it was run. I'm
         * not convinced this is the best place for this to happen, though.
         */
        currentTime = 0;
        inputData(); // get machine and job data
        startShop(); // initial machine loading
        simulate(); // run all jobs through shop
        outputStatistics(); // output machine wait times
    }
}