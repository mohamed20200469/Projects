package Java.SW_assignment;

import java.util.*;

public class GarageController {
    Garage garage1;
    static GarageController instance = new GarageController();

    private GarageController() {
    }

    static GarageController getInstance() {
        return instance;
    }

    // inserts vehicle using parkIn function and slot configuration index identifier
    public void insertVehicle(Vehicle car, int method) {
        if (garage1.getNumOfSlots() > garage1.getNumOfVehicles()) {
            if (method == 1) {
                FirstServed parking = FirstServed.getInstance();
                garage1.ParkingLot
                        .get(parking.park(car, garage1.ParkingLot, garage1.getNumOfSlots()))
                        .parkIn(car);
            } else if (method == 2) {
                BestFit parking = BestFit.getInstance();
                garage1.ParkingLot
                        .get(parking.park(car, garage1.ParkingLot, garage1.getNumOfSlots()))
                        .parkIn(car);
            }
            garage1.setNumOfVehicles(garage1.getNumOfVehicles() + 1);
        } else {
            System.out.println("ParkIn failed... garage is full!");
        }
        System.out.println("--------------");
    }

    // prints all available parking slots
    public void displayAvailableSlots() {
        System.out.println("--------------");
        if (garage1.getNumOfSlots() == garage1.getNumOfVehicles()) {
            System.out.println("No available parking slots!");
        } else {
            for (SlotController s : garage1.ParkingLot) {
                s.displayAvailable();
            }
        }
        System.out.println("--------------");
    }

    // sets the vehicle data of the vehicle to be parked
    public Vehicle customVehicle() {
        System.out.println("--------------");
        Scanner input1 = new Scanner(System.in);
        String mName;
        System.out.println("Enter modelName: ");
        mName = input1.nextLine();
        int vID;
        System.out.println("Enter vehicle ID: ");
        vID = input1.nextInt();
        int mYear;
        System.out.println("Enter model year: ");
        mYear = input1.nextInt();
        float v_Depth;
        System.out.println("Enter vehicle depth: ");
        v_Depth = input1.nextFloat();
        float v_Width;
        System.out.println("Enter vehicle width: ");
        v_Width = input1.nextFloat();
        Vehicle v1 = new Vehicle(mName, vID, mYear, v_Depth, v_Width);
        System.out.println("--------------");
        return v1;
    }

    // inserts custom slot in the garage
    public void createCustomSlot() {
        System.out.println("--------------");
        garage1.customSlot();
        System.out.println("--------------");
    }

    // displays vehicle in parking slot
    public void findVehicle(int slotNum) {
        System.out.println("--------------");
        if (garage1.ParkingLot.get(slotNum).getSlot().getAvailability() == true) {
            System.out.println("Slot is empty!");
        } else {
            garage1.ParkingLot.get(slotNum).displayVehicleInSlot();
        }
        System.out.println("--------------");
    }

    // checks if slot in index is empty
    public boolean isSlotEmpty(int slotNum) {
        if (garage1.ParkingLot.get(slotNum).getSlot().getAvailability() == false) {
            return false;
        } else {
            return true;
        }
    }

    // removes the vehicle using parkOut function
    public void removeVehicle(int slotNum) {
        System.out.println("--------------");
        if (slotNum >= 0 && slotNum < garage1.getNumOfSlots()) {
            if (garage1.ParkingLot.get(slotNum).getSlot().getAvailability() == false) {
                garage1.ParkingLot.get(slotNum).parkOut();
                garage1.setNumOfVehicles(garage1.getNumOfVehicles() - 1);
                ParkingFeeCalculator pFC = new ParkingFeeCalculator();
                Income.setTotalIncome(Income.getTotalIncome() + pFC.findFee(garage1.ParkingLot.get(slotNum)));
                System.out.println("Parking fees: " + pFC.findFee(garage1.ParkingLot.get(slotNum)) + " Time spent: "
                        + (pFC.getPFee().getHours() + 1) + " hours.");
            } else {
                System.out.println("Parkout failed... no vehicle in slot!");
            }
        } else {
            System.out.println("Parkout failed... no such slot!");
        }
        System.out.println("--------------");
    }

    // prints number of vehicles currently in garage
    public void displayNumOfVehicles() {
        System.out.println("--------------");
        System.out.println("Number of vehicles currently in garage: " + garage1.getNumOfVehicles());
        System.out.println("--------------");
    }

    // prints current total income
    public void printIncome() {
        System.out.println("--------------");
        System.out.println("Garage income at this moment: " + Income.getTotalIncome());
        System.out.println("--------------");
    }

    // initialises garage info
    public void setGarageInfo(int num, float dep, float wid) {
        garage1 = Garage.getInstance();
        garage1.setNumOfSlots(num);
        for (int i = 0; i < garage1.getNumOfSlots(); i++) {
            ParkingSlot slot = new ParkingSlot(dep, wid);
            SlotController slot1 = new SlotController(slot);
            garage1.ParkingLot.add(slot1);
        }
    }
}