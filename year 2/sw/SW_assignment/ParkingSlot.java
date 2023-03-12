package Java.SW_assignment;

import java.time.LocalTime;

public class ParkingSlot {
    static int number = 100;
    private int slotNumber;
    private float sDepth;
    private float sWidth;
    private boolean available;
    private Vehicle car;
    private LocalTime parkTime;
    private LocalTime departTime;

    //Default constructor
    public ParkingSlot() {
        slotNumber = number;
        number++;
        sDepth = 22f;
        sWidth = 5.8f;
        available = true;
    }

    public ParkingSlot(float dep, float wid) {
        slotNumber = number;
        number++;
        sDepth = dep;
        sWidth = wid;
        available = true;
    }

    public void setDepth(float dep) {
        sDepth = dep;
    }

    public void setWidth(float wid) {
        sWidth = wid;
    }

    public void setAvailability(boolean state) {
        available = state;
    }

    public int getSlotNumber() {
        return this.slotNumber;
    }

    public float getSlotDepth() {
        return this.sDepth;
    }

    public float getSlotWidth() {
        return this.sWidth;
    }

    public boolean getAvailability() {
        return this.available;
    }

    public Vehicle getCarInSlot() {
        return car;
    }

    public void setCarInSlot(Vehicle car1) {
        this.car = car1;
    }

    public void setParkTime(LocalTime t1) {
        parkTime = t1;
    }

    public LocalTime getParkTime() {
        return parkTime;
    }

    public void setDepartTime(LocalTime t1) {
        departTime = t1;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }
}