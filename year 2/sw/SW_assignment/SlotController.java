package Java.SW_assignment;

public class SlotController {
    private ParkingSlot slot;

    //Parametrised constructor
    SlotController(ParkingSlot slot1) {
        slot = slot1;
    }

    //Show info of vehicle in slot
    public void displayVehicleInSlot() {
        System.out.println("car model:" + slot.getCarInSlot().getModelName());
        System.out.println("ID: " + slot.getCarInSlot().getVehicleID());
        System.out.println("Model Year: " + slot.getCarInSlot().getModelYear());
    }

    //Checks if slot is available... if true, print it
    public void displayAvailable() {
        if (slot.getAvailability() == true) {
            System.out.println("Slot " + slot.getSlotNumber() + " is vacant " + "(Dimensions are " + slot.getSlotDepth()
                    + " depth and "
                    + slot.getSlotWidth() + " width).");
        }
    }

    //Takes vehicle as argument and places it in the slot... changing all telated attributes
    public void parkIn(Vehicle car1) {
        if (slot.getSlotDepth() >= car1.getVehicleDepth() && slot.getSlotWidth() >= car1.getVehicleWidth()) {
            slot.setCarInSlot(car1);
            slot.setAvailability(false);
            slot.setParkTime(java.time.LocalTime.now());
            System.out.println(
                    "ParkIn successful... Slot number:" + slot.getSlotNumber() + "... Time:" + slot.getParkTime());
        }
        else {
            System.out.println("ParkIn failed... no suitable slot!");
        }
    }

    //Removes vehicle from slot... changing all related attributes
    public void parkOut() {
            slot.setCarInSlot(null);
            slot.setAvailability(true);
            slot.setDepartTime(java.time.LocalTime.now());
            System.out.println("ParkOut successful... Slot number:"+ slot.getSlotNumber() + "... Time:" + slot.getDepartTime());
    }

    public ParkingSlot getSlot() {
        return slot;
    }
}