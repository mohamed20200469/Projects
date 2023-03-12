package Java.SW_assignment;

import java.util.ArrayList;

//SuperClass
interface SlotConfiguration {
    //Overriden function
    public int park(Vehicle car, ArrayList<SlotController> garage, int numOfSlots);
}