package Service;

import Data.PatientRepository;
import Users.Patient;

public class PatientService extends UserService<Patient,PatientRepository> {
    PatientRepository patientRepository;
    Patient currentPatient;

    public PatientService(String userId) {
        patientRepository = new PatientRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
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
