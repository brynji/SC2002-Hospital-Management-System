import Data.*;
import Menus.*;
import Misc.RoleType;
import Service.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.nio.file.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static Map<RoleType, IMenu> menus;
    public static HospitalManagementApp hospital;

    public static void main(String[] args) throws IOException {
        Path savedTestData = Paths.get("Tests/SavedTestData");
        Path initialData = Paths.get("Tests/InitialData");
        deleteDirectoryContents(savedTestData);
        copyDirectory(initialData, savedTestData);
        DataSource dataSource = new Database("Tests/SavedTestData");
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
        String inputWithDynamicDates = preprocessPlaceholders(inputLines);

        System.setIn(new ByteArrayInputStream(inputWithDynamicDates.getBytes()));
//        System.setIn(new ByteArrayInputStream(String.join("\n", inputWithDynamicDates).getBytes()));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            hospital.mainMenu();

        } catch (Exception e) {
            originalOut.println("Test "  + " xxxx"+e.getMessage());
            System.setOut(originalOut);
            return false;
        }

        System.setOut(originalOut);

        List<String> soutput = outputStream.toString().lines().map(String::trim).toList();

        var output = String.join("\n", soutput).replace("\r\n", "\n");
        output = output.replaceAll("ID: [a-zA-Z0-9-]{16,}", "ID: <dynamic>");
        output = output.replaceAll("PrescriptionId: [a-zA-Z0-9-]{16,}", "PrescriptionId: <dynamic>");

        System.out.println("outputStream start =============");
        System.out.println(output);
        System.out.println("outputStream end =============");

        List<String> expectedLines = Files.readAllLines(expectedOutputFile).stream().map(String::trim).toList();
        String expectedWithDynamicDates = preprocessPlaceholders(expectedLines);
        String expectedOutput = (expectedWithDynamicDates).replace("\r\n", "\n");
//        String expectedOutput = String.join("\n", expectedWithDynamicDates).replace("\r\n", "\n");

/*
        System.out.println("-----s----------");
        System.out.println(expectedOutput);
        System.out.println("-----e----------");
*/
        return output.equals(expectedOutput); // Return comparison result
    }


    // Deletes all contents of a directory
    private static void deleteDirectoryContents(Path dir) throws IOException {
        Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    // Recursively copies files from one directory to another
    private static void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source)
                .forEach(src -> {
                    try {
                        Path dest = target.resolve(source.relativize(src));
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static String preprocessPlaceholders(List<String> lines) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate at = today.plusDays(2);  // 'at' is 'after tomorrow'
        LocalDate aat = today.plusDays(3);
        LocalDate aaat = today.plusDays(4);
        LocalDate aaaat = today.plusDays(5);
        LocalDate aaaaat = today.plusDays(6);

        return lines.stream()
                .map(line -> line.replace("{{TODAY}}", today.toString()))
                .map(line -> line.replace("{{TOMORROW}}", tomorrow.toString()))
                .map(line -> line.replace("{{AT}}", at.toString()))
                .map(line -> line.replace("{{AAT}}", aat.toString()))
                .map(line -> line.replace("{{AAAT}}", aaat.toString()))
                .map(line -> line.replace("{{AAAAT}}", aaaat.toString()))
                .map(line -> line.replace("{{AAAAAT}}", aaaaat.toString()))
                .collect(Collectors.joining("\n"));
    }

}
