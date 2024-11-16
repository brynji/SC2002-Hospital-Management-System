package Tests;

import Data.*;
import Menus.*;
import Misc.RoleType;
import Service.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.nio.file.*;
import java.util.stream.Stream;

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

    static void testAll() {
        boolean testResult;
        int allTests = 0;
        int passedTests = 0;
        Path inputDir = Paths.get("Tests/Inputs");
        Path outputDir = Paths.get("Tests/Outputs");

        try (Stream<Path> inputFiles = Files.list(inputDir)) {
            for(var file : inputFiles.toList()){
                Path expectedOutputFile = outputDir.resolve(file.getFileName());
                if (!(Files.exists(expectedOutputFile))) {
                    System.out.println("No matching output file found for: " + file.getFileName());
                    return;
                }
                testResult = test(file,expectedOutputFile);
                if (testResult) {
                    passedTests++;
                    System.out.println("Test " + file.getFileName() + " passed");//TODO
                } else { System.out.println("Test " + file.getFileName() + " failed"); }
                allTests++;
            }
        } catch (IOException e) { System.out.println("Error: Problem resolving files"+e.getMessage()); }
        System.out.println("-------------");
        System.out.println(passedTests+" of "+allTests+" tests passed");
    }

    static boolean test(Path input, Path expectedOutputFile) throws IOException {
        List<String> inputLines = Files.readAllLines(input);
        System.setIn(new ByteArrayInputStream(String.join("\n", inputLines).getBytes()));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            hospital.mainMenu();

            originalOut.println("outputStream start =============");
            originalOut.println(outputStream);
            originalOut.println("outputStream end =============");

        } catch (Exception e) {
            System.setOut(originalOut);
            return false;
        }

        System.setOut(originalOut);

        String output = outputStream.toString().replace("\r\n", "\n").trim();

        List<String> expectedLines = Files.readAllLines(expectedOutputFile);
        String expectedOutput = String.join("\n", expectedLines).replace("\r\n", "\n").trim();

        return output.equals(expectedOutput); // Return comparison result
    }

}
