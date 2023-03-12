package Java.SW_assignment;

public class Income {
    static float totalIncome;
    static Income instance = new Income();

    //Default constructor
    private Income() {
        totalIncome = 0f;
    }

    static Income getInstance() {
        return instance;
    }

    static void setTotalIncome(float i) {
        totalIncome = i;
    }
    
    static float getTotalIncome() {
        return totalIncome;
    }
}
