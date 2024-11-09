import Data.*;
import Menus.*;
import Misc.RoleType;
import Service.*;

import java.util.Map;

public class main {
    public static void main(String[] args) {
        DataSource dataSource = new Database();
        ILoginService loginService = new LoginService(dataSource);
        Map<RoleType, IMenu> menus = Map.of(
                RoleType.Patient, new PatientMenu(new PatientService(new PatientRepository(dataSource))),
                RoleType.Doctor, new DoctorMenu(new DoctorService(new DoctorRepository(dataSource))),
                RoleType.Pharmacist, new PharmacistMenu(new PharmacistService(new PharmacistRepository(dataSource))),
                RoleType.Administrator, new AdministratorMenu(new AdministratorService(new AdministratorRepository(dataSource))));

        HospitalManagementApp hospital = new HospitalManagementApp(menus,loginService);
        hospital.mainMenu();
    }
}
