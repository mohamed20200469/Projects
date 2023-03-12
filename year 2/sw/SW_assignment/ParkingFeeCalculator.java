package Java.SW_assignment;
import static java.time.temporal.ChronoUnit.MINUTES;

public class ParkingFeeCalculator {
    ParkingFee pFee;

    //Default constructor
    ParkingFeeCalculator() {
        pFee = new ParkingFee();
    }

    //Fare is 5 pounds per minute just for test reasons
    //returns the fee of a certain parking slot after parkOut
    public float findFee(SlotController sC) {
        pFee.setHours(sC.getSlot().getParkTime().until(sC.getSlot().getDepartTime(), MINUTES));
        pFee.setFees(pFee.getHours() * pFee.getRate() + 5);
        return pFee.getFees();
    }

    public ParkingFee getPFee() {
        return pFee;
    }
}
