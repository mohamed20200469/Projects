package Java.SW_assignment;

public class Vehicle {
    private String modelName;
    private int vehicleID;
    private int modelYear;
    private float vDepth;
    private float vWidth;

    public Vehicle()
    {
        modelName = "Nissan";
        vehicleID = 999;
        modelYear = 2020;
        vWidth = 5f;
        vDepth = 20f;
    }

    public Vehicle(String name, int ID, int year, float dep, float wid)
    {
        modelName = name;
        vehicleID = ID;
        modelYear = year;
        vWidth = wid;
        vDepth = dep;
    }

    public void setModelName(String mName) {
        modelName = mName;
    }

    public void setModelYear(int mYear) {
        modelYear = mYear;
    }

    public void setVehicleID(int vID) {
        vehicleID = vID;
    }

    public void setVehicleWidth(float W) {
        vWidth = W;
    }

    public void setVehicleDepth(float D) {
        vDepth = D;
    }

    public float getVehicleDepth() {
        return this.vDepth;
    }

    public String getModelName() {
        return this.modelName;
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public int getVehicleID() {
        return this.vehicleID;
    }

    public float getVehicleWidth() {
        return this.vWidth;
    }

}