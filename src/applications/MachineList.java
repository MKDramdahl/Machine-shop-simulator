package applications;

public class MachineList {

	private static Machine[] machineArray;
	
    public static void createMachineArray(){
        machineArray = new Machine[MachineShopSimulator.getNumberOfMachines()];
        for (int i = 0; i < MachineShopSimulator.getNumberOfMachines(); i++)
            machineArray[i] = new Machine(i);
    }
        
    public static Machine getMachine(int index){
    	return machineArray[index];
    }
}
