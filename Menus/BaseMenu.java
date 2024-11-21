package Menus;

import Service.IService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Abstract base class for creating menu-driven user interfaces.
 * Provides shared functionality for handling user actions such as updating personal information,
 * changing passwords, and selecting options from a list.
 *
 * @param <T> the type of service associated with the menu, which must implement the IService interface.
 */
public abstract class BaseMenu<T extends IService> implements IMenu<T> {

    /** Scanner object for reading user input. */
    protected Scanner sc;

    /**
     * Sets up the menu for the current user and handles first login requirements.
     * If this is the user's first login, prompts them to change their password.
     *
     * @param currentUserId the ID of the current user.
     * @param sc the Scanner object for reading user input.
     */
    public void baseMenu(String currentUserId, Scanner sc) {
        this.sc = sc;
        getUserService().setCurrentUser(currentUserId);
        if(getUserService().getCurrentUser().getFirstLogin()){
            System.out.println("This is your first login, please change your password");
            changePassword();
            getUserService().getCurrentUser().setFirstLogin(false);
            getUserService().getRepository().save();
        }
    }

    /**
     * Reads next int from input, handling exception if input is not int
     * @return Integer input from user
     */
    protected int nextInt(){
        int in;
        try{
            in = Integer.parseInt(sc.nextLine());
        } catch(Exception e){
            System.out.println("Invalid input, please try again");
            return nextInt();
        }
        return in;
    }

    /**
     * Prompts the user to change their password and updates it in the system.
     */
    public void changePassword() {
        System.out.print("Enter new password: ");
        String passwd = sc.nextLine();
        getUserService().changePassword(passwd);
        System.out.println("Your new password is " + passwd);
    }

    /**
     * Provides the user with options to update their personal information (e.g., name, email, contact number).
     * Updates the chosen field using the corresponding service method.
     */
    public void updatePersonalInformation() {
        ArrayList<String> options = new ArrayList<>(List.of("name", "email", "contact number"));
        System.out.println("Update of personal information");
        int choice = printAllAndChooseOne(options);
        System.out.print("Enter your new " + options.get(choice) + ": ");
        String newField = sc.nextLine();
        switch(choice){
            case 0 -> getUserService().updateName(newField);
            case 1 -> getUserService().updateEmail(newField);
            case 2 -> getUserService().updateContactNumber(newField);
        }
        System.out.println("Your new " + options.get(choice) + " is " + newField);
    }

    /**
     * Displays a list of options and prompts the user to select one.
     * Ensures the user's selection is valid before proceeding.
     *
     * @param objects the collection of options to display.
     * @return the index of the selected option, or -1 if the collection is empty.
     */
    protected int printAllAndChooseOne(Collection<?> objects) {
        if (objects.isEmpty())
            return -1;
        int i = 1;
        for (Object o : objects) {
            System.out.println(i + " " + o);
            i++;
        }
        System.out.print("Enter your choice: ");
        int choice = nextInt() - 1;
        while(choice<0 || choice>=objects.size()){
            System.out.println("Invalid choice, try again");
            choice = nextInt() - 1;
        }
        sc.nextLine(); // Consume newline
        return choice;
    }
}