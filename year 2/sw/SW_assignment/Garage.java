package Java.SW_assignment;

import java.util.*;

public class Garage {
    public ArrayList<SlotController> ParkingLot;
    int numOfSlots;
    int numOfVehicles;
    static Garage instance = new Garage();

    public static Garage getInstance() {
        return instance;
    }
    private Garage() {
        ParkingLot = new ArrayList<SlotController>();
        numOfSlots = 0;
        numOfVehicles = 0;
    }

    public int getNumOfSlots() 
    {
        return numOfSlots;
    }

    //Add customised parking slot through the slot controller
    public void customSlot() {
        Scanner input2 = new Scanner(System.in);
        float sDepth, sWidth;
        System.out.println("Enter depth: ");
        sDepth = input2.nextFloat();
        System.out.println("Enter sWidth: ");
        sWidth = input2.nextFloat();
        ParkingSlot slot = new ParkingSlot(sDepth, sWidth);
        SlotController slotC = new SlotController(slot);
        ParkingLot.add(slotC);
        numOfSlots++;
    }

    public void setNumOfVehicles(int num) {
        numOfVehicles = num;
    }

    public int getNumOfVehicles() {
        return numOfVehicles;
    }

    public void setNumOfSlots(int num) {
        numOfSlots = num;
    }
}
