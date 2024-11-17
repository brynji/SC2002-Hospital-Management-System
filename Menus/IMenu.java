package Menus;

import java.util.Scanner;

/**
 * Interface defining the structure for menu implementations in the Hospital Management System.
 * Enforces consistency across role-specific menus by requiring methods for accessing services
 * and defining the base menu logic.
 *
 * @param <T> the type of service associated with the menu.
 */
public interface IMenu<T> {

    /**
     * Retrieves the service associated with the menu.
     * Each menu uses a specific service to handle business logic and data operations.
     *
     * @return the service instance associated with the menu.
     */
    T getUserService();

    /**
     * Displays the main menu for the associated role and handles user input to perform operations.
     *
     * @param currentUserId the ID of the currently logged-in user.
     * @param sc the Scanner instance for reading user input.
     */
    void baseMenu(String currentUserId, Scanner sc);
}