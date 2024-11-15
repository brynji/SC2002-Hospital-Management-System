package Menus;

import Data.AdministratorRepository;
import Misc.MedicalRecord;
import Misc.RoleType;
import Service.AdministratorService;
import Service.ILoginService;
import Users.Administrator;
import Users.Doctor;
import Users.Patient;
import Users.Pharmacist;

import java.util.Map;
import java.util.Scanner;


public class HospitalManagementApp {
    private final Map<RoleType,IMenu> menus;
    private final ILoginService loginService;

    public HospitalManagementApp(Map<RoleType, IMenu> menus, ILoginService loginService) {
        this.menus = menus;
        this.loginService = loginService;
    }

    /*
        pharma view empty AOR
        printAllAndChoose na empty

        patient change passwd
        medical record vypis info
     */

    public void mainMenu() {
        //AdministratorRepository admin = ((AdministratorService) menus.get(RoleType.Administrator).getUserService()).getRepository();
        //admin.addNew(new Administrator("admin","new admin","jedi","12/11/1568","mail","56"));
        //admin.addNew(new Doctor("doctor","doc","snail","10/02/2015","sa","468",false));
        //admin.addNew(new Patient("patient","sdf","a@d.s","156",new MedicalRecord("patient","sdf","12/04/2004","sova","red")));
        //admin.addNew(new Pharmacist("pharma","pha","klokan","29/03/2013","ph@ds.cz","456"));
        //admin.save();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("HOSPITAL SYSTEM");
            System.out.println("1 LOGIN");
            System.out.println("2 QUIT");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            if(choice!=1) {
                return;
            }

            System.out.print("Enter userID: ");
            String userId = sc.next();
            System.out.print("Enter password: ");
            String password = sc.next();

            RoleType role = loginService.login(userId, password);
            if(role == RoleType.None){
                System.out.println("Invalid credentials\n");
                continue;
            }
            menus.get(role).baseMenu(userId,sc);
            System.out.println();
        }
    }
}