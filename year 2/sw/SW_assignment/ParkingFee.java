package Java.SW_assignment;

public class ParkingFee {
    private long hours;
    private float fees;
    private float rate = 5f;

    //Default constructor
    ParkingFee() {
        fees = 0;
        hours = 0;
    }

    public void setHours(long h) {
        hours = h;
    }

    public long getHours() {
        return hours;
    }

    public void setFees(float f) {
        fees = f;
    }

    public float getFees() {
        return fees;
    }

    public void setRate(float r) {
        rate = r;
    }

    public float getRate() {
        return rate;
    }
}