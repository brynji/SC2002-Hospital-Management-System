package Project.Menus;
import java.util.Scanner;

public abstract class DisplayMenu {
        public abstract void displayMenu();

    protected int getInput() { // Use protected int so that only subclasses that extend it can use the method

        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose an option: ");
        int input = sc.nextInt();

        return input;

    }


}
