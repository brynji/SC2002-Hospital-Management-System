package Menus;

import java.util.Scanner;

public interface IMenu<T> {
    T getUserService();
    void baseMenu(String currentUserId, Scanner sc);
}
