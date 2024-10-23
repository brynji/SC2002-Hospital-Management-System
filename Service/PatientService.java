package Service;

import Data.PatientRepository;
import Misc.RoleType;
import Users.Patient;

public class PatientService extends UserService<Patient,PatientRepository> {
    PatientRepository repository;
    Patient currentUser;

    public PatientService(String userId) {
        repository = new PatientRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Patient);
    }

    @Override
    public Patient getCurrentUser() {
        return currentUser;
    }

    @Override
    public PatientRepository getRepository() {
        return repository;
    }
}
