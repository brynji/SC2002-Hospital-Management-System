package Service;

import Data.PatientRepository;
import Users.Patient;

public class PatientService extends UserService {
    PatientRepository patientRepository;
    Patient currentPatient;

    public PatientService() {
        patientRepository = new PatientRepository();
    }

    @Override
    public void SetCurrentUser(String userId) {
        currentPatient = patientRepository.FindPatientById(userId);
    }

    @Override
    public void ChangePassword(String newPassword){
        currentPatient.setPassword(newPassword);
        patientRepository.Save();
    }
}
