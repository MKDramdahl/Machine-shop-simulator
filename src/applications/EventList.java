package applications;

public class EventList {

    // data members
    private int[] finishTime; // finish time array

    // constructor
    public EventList(int theNumMachines, int theLargeTime) {// initialize finish times for m machines
        if (theNumMachines < 1)
            throw new IllegalArgumentException(MachineShopSimulator.NUMBER_OF_MACHINES_MUST_BE_AT_LEAST_1);
        finishTime = new int[theNumMachines + 1];

        // all machines are idle, initialize with large finish time
        for (int i = 0; i < theNumMachines; i++)
            finishTime[i] = theLargeTime;
    }
}