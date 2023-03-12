package Java.SW_assignment;

import java.util.ArrayList;

//SubClass
public class BestFit implements SlotConfiguration {

    static BestFit instance = new BestFit();

    private BestFit() {

    }

    static BestFit getInstance() {
        return instance;
    }
    
    public int park(Vehicle car, ArrayList<SlotController> garage, int numOfSlots) {
        float leastDepth = 999f;
        float leastWidth = 999f;
        int index = 0;
        for (int i = 0; i < numOfSlots; i++) {
            if (garage.get(i).getSlot().getAvailability() == true) {
                if (garage.get(i).getSlot().getSlotWidth() >= car.getVehicleWidth()
                        && garage.get(i).getSlot().getSlotDepth() >= car.getVehicleDepth()) {
                    if (leastDepth > garage.get(i).getSlot().getSlotDepth() && leastWidth > garage.get(i).getSlot().getSlotWidth()) {
                        leastDepth = garage.get(i).getSlot().getSlotDepth();
                        leastWidth = garage.get(i).getSlot().getSlotWidth();
                        index = i;
                    }
                }
            }
        }
        return index;
    }
}