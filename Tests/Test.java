package Tests;

import Data.*;
import Menus.*;
import Misc.RoleType;
import Service.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

public class Test {
    public static Map<RoleType, IMenu> menus;
    public static HospitalManagementApp hospital;

    public static void main(String[] args) {
        DataSource dataSource = new TestDataSource();
        ILoginService loginService = new LoginService(dataSource);
        menus = Map.of(
                RoleType.Patient, new PatientMenu(new PatientService(new PatientRepository(dataSource))),
                RoleType.Doctor, new DoctorMenu(new DoctorService(new DoctorRepository(dataSource))),
                RoleType.Pharmacist, new PharmacistMenu(new PharmacistService(new PharmacistRepository(dataSource))),
                RoleType.Administrator, new AdministratorMenu(new AdministratorService(new AdministratorRepository(dataSource))));

        hospital = new HospitalManagementApp(menus,loginService);

        testAll();
    }

    static void testAll(){
        boolean testResult = false;
        int allTests = 1;
        int passedTests = 0;

        //for all tests
        testResult = test("2\n","HOSPITAL SYSTEM\n1 LOGIN\n2 QUIT\n");
        if(testResult){
            passedTests++;
            System.out.println("Test ... passed");
        } else {
            System.out.println("Test ... failed");
        }
        System.out.println("-------------");
        System.out.println(passedTests+" of "+allTests+" tests passed");
    }

    static boolean test(String input, String expectedOutput){
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        hospital.mainMenu();

        System.setOut(originalOut);
        String output = outputStream.toString().replace("\r\n", "\n").trim();
        expectedOutput = expectedOutput.replace("\r\n", "\n").trim();

        return expectedOutput.equals(output);
    }
}
