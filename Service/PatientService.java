package Service;

import Data.PatientRepository;
import Users.Patient;

public class PatientService extends UserService<Patient,PatientRepository> {
    PatientRepository patientRepository;
    Patient currentPatient;

    public PatientService() {
        patientRepository = new PatientRepository();
    }

    @Override
    public void SetCurrentUser(String userId) {
        currentPatient = patientRepository.FindById(userId);
    }

    @Override
    public Patient getCurrentUser() {
        return currentPatient;
    }

    @Override
    public PatientRepository getRepository() {
        return patientRepository;
    }
}
