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
    private static int numberOfJobs; // number of jobs
    private static EventList eventList; // pointer to event list

    // methods
    public static int getNumberOfJobs() {
    	return numberOfJobs;
    }
    
    public static int getCurrentTime() {
    	return currentTime;
    }
    
    public static EventList getEventList() {
    	return eventList;
    }
    
    /** input machine shop data */
    static void inputData() {
        // define the input stream to be the standard input stream
        MyInputStream keyboard = new MyInputStream();

        System.out.println("Enter number of machines and jobs");
        MachineList.setNumberOfMachines(keyboard.readInteger());
        numberOfJobs = keyboard.readInteger();
        if (MachineList.getNumberOfMachines() < 1 || numberOfJobs < 1)
            throw new MyInputException(NUMBER_OF_MACHINES_AND_JOBS_MUST_BE_AT_LEAST_1);

        // create event and machine queues
        eventList = new EventList(MachineList.getNumberOfMachines(), Integer.MAX_VALUE);
        MachineList.createMachineArray();

        // input the change-over times
        MachineList.getChangeOverInput(keyboard);

        // input the jobs
        MachineList.getJobsInput(keyboard);
    }

    /** load first jobs onto each machine */
    static void startShop() {
        for (int p = 0; p < MachineList.getNumberOfMachines(); p++)
            MachineList.getMachine(p).changeState();
    }

    /** process all jobs to completion */
    static void simulate() {
        while (numberOfJobs > 0) {// at least one job left
            int nextToFinish = eventList.nextEventMachine();
            currentTime = eventList.nextEventTime(nextToFinish);
            // change job on machine nextToFinish
            Job theJob = MachineList.getMachine(nextToFinish).changeState();
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
        for (int p = 0; p < MachineList.getNumberOfMachines(); p++) {
        	machineID = p + 1;
            System.out.println("Machine " + machineID + " completed "
                    + MachineList.getMachine(p).getNumberOfTasks() + " tasks");
            System.out.println("The total wait time was "
                    + MachineList.getMachine(p).getTotalWait());
            System.out.println();
        }
    }

    /** entry point for machine shop simulator */
    public static void main(String[] args) {
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