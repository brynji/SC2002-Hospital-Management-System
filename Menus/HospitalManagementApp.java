package Menus;

import Data.AdministratorRepository;
import Service.AdministratorService;
import Service.ILoginService;
import Misc.*;
import Users.Administrator;
import Users.Doctor;
import Users.Patient;

import java.util.Map;
import java.util.Scanner;


public class HospitalManagementApp {
    private final Map<RoleType,IMenu> menus;
    private final ILoginService loginService;

    public HospitalManagementApp(Map<RoleType, IMenu> menus, ILoginService loginService) {
        this.menus = menus;
        this.loginService = loginService;
    }

    public void mainMenu() {
        //AdministratorRepository admin = ((AdministratorService) menus.get(RoleType.Administrator).getUserService()).getRepository();
        //admin.addNew(new Administrator("admin","new admin","jedi","1212-12-10","mail","56"));
        //admin.addNew(new Doctor("doctor","doc","snail","1086-01-29","sa","468"));
        //admin.addNew(new Patient("patient","sdf","a@d.s","156","5",new MedicalRecord("5","sdf","20002-06-12","","")));
        //admin.save();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("HOSPITAL SYSTEM");
            System.out.println("1 LOGIN");
            System.out.println("2 QUIT");

            int choice = sc.nextInt();
            if(choice!=1) {
                return;
            }

            System.out.println("Enter userID");
            String userId = sc.next();
            System.out.println("Enter password");
            String password = sc.next();

            RoleType role = loginService.login(userId, password);
            if(role == RoleType.None){
                System.out.println("Invalid credentials");
                continue;
            }
            menus.get(role).baseMenu(userId,sc);
            System.out.println();
        }
    }
}