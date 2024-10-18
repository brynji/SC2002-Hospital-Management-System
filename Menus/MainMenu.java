package Menus;

import Data.LoginRepository;
import Misc.*;

import java.io.IOException;
import java.util.Scanner;


public class MainMenu {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("HOSPITAL SYSTEM");
            System.out.println("1 LOGIN");
            System.out.println("2 QUIT");
            int choice = sc.nextInt();
            if(choice == 2) { return;}
            System.out.println("Type userID");
            String userId = sc.next();
            System.out.println("Type password");
            String password = sc.next();
            RoleType roles = LoginRepository.CheckCredentials(userId, password);
            switch (roles) {
                case None:
                    System.out.println("invalid credentials");
                    break;
                case Patient:
                    PatientMenu patientMenu = new PatientMenu(userId);
                    patientMenu.baseMenu();
                    break;
                case Doctor:
                    DoctorMenu doctorMenu = new DoctorMenu(userId);
                    doctorMenu.baseMenu();
                    break;
                case Pharmacist:
                    PharmacistMenu pharmacistMenu = new PharmacistMenu(userId);
                    pharmacistMenu.baseMenu();
                    break;
                case Administrator:
                    AdministratorMenu adminMenu = new AdministratorMenu(userId);
                    adminMenu.baseMenu();
                    break;
            }
        }
    }
}