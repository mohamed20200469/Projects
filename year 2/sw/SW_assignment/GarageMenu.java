package Java.SW_assignment;

import java.util.*;

public class GarageMenu {
    public static void main(String[] args) {
        menu();
    }

    static void menu() {
        int option, config;
        GarageController gController1;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Parking lot management system!");
        System.out.print("Please enter number of parking slots in garage:");
        int num = input.nextInt();
        System.out.print("Please enter parking slot depth:");
        float dep = input.nextFloat();
        System.out.print("Please enter parking slot width:");
        float wid = input.nextFloat();
        if (num > 0 && num < 100 && wid >= 5 && wid <= 8 && dep >= 22 && dep <= 28) {
            gController1 = GarageController.getInstance();
            gController1.setGarageInfo(num, dep, wid);
            System.out.println("Please enter ParkIn configuration...\n1 for first served\n2 for best configuration");
            config = input.nextInt();
            if (config == 1 || config == 2) {
                do {
                    System.out.println("Please select an option from the following:");
                    System.out.println(
                            "1- ParkIn vehicle. \n2- ParkOut vehicle in slot. \n3- Find car in a parking slot.\n4- Insert custom parking slot."
                                    +
                                    "\n5- Display available parking slots.\n6- Display number of vehicles currently in garage.\n7- Display garage income.\n8- Exit system.");
                    option = input.nextInt();
                    switch (option) {
                        case 1:
                            gController1.insertVehicle(gController1.customVehicle(), config);
                            break;
                        case 2:
                            int slot2;
                            System.out.println("Enter slot number: ");
                            slot2 = input.nextInt();
                            slot2 -= 100;
                            gController1.removeVehicle(slot2);
                            break;
                        case 3:
                            int slot1;
                            System.out.println("Enter slot number: ");
                            slot1 = input.nextInt();
                            slot1 -= 100;
                            gController1.findVehicle(slot1);
                            break;
                        case 4:
                            gController1.createCustomSlot();
                            break;
                        case 5:
                            gController1.displayAvailableSlots();
                            break;
                        case 6:
                            gController1.displayNumOfVehicles();
                            break;
                        case 7:
                            gController1.printIncome();
                            break;
                        case 8:
                            System.out.println("Exiting system...");
                            break;
                        default:
                            System.out.println("Wrong config!");
                            break;
                    }
                } while (option != 8);
            } else {
                System.out.println("Wrong configuration choice!");
            }
        } else {
            System.out.println(
                    "Wrong input! (number of slots between 0 and 100, width between 5 and 8 feet, depth between 22 and 28 feet)");
        }
    }
}