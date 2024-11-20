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

    public int nextInt(Scanner sc) {
        int in;
        try{
            in = Integer.parseInt(sc.nextLine());
        } catch(Exception e){
            System.out.println("Invalid input, please try again");
            return nextInt(sc);
        }
        return in;
    }

    public void mainMenu() {
        /*
            //password set to "{userID}p"
        AdministratorRepository admin = ((AdministratorService) menus.get(RoleType.Administrator).getUserService()).getRepository();
        admin.addNew(new Administrator("admin","Taylor Swift","female","13/10/1989","mail@example.sg","56"));
        admin.addNew(new Doctor("doctor","Priyanka Chopra","female","10/02/1978","ex@seznam.cz","46887245",false));
        admin.addNew(new Doctor("doctor0","Bebe Rexa","female","10/02/2000","mymail@mail.sg","46887245",true));
        admin.addNew(new Patient("patient","Bob Dylan","newmail@dgmail.cz","88775869",new MedicalRecord("patient","Bob Dylan","12/04/1923","male","A+")));
        admin.addNew(new Patient("patient0","Pepe Cena","exchange@mailing.cz","78531649",new MedicalRecord("patient0","Pepe Cena","22/08/1983","male","B+")));
        admin.addNew(new Pharmacist("pharmacist","Alex Baldwin","male","29/03/1999","phar@ems.cz","45776956"));
        admin.save();
        */
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("HOSPITAL SYSTEM");
            System.out.println("1 LOGIN");
            System.out.println("2 QUIT");
            System.out.print("Enter your choice: ");
            int choice = nextInt(sc);
            if(choice!=1) {
                return;
            }

            System.out.print("Enter userID: ");
            String userId = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

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