package Service;

import Data.DoctorRepository;
import Misc.RoleType;
import Users.Doctor;

public class DoctorService extends UserService<Doctor, DoctorRepository> {
    private DoctorRepository repository;
    private Doctor currentUser;
    //List<Patient> patients; ???

    public DoctorService(String userId) {
        repository = new DoctorRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.findUserById(userId, RoleType.Doctor);
    }

    @Override
    public Doctor getCurrentUser() {
        return currentUser;
    }

    @Override
    public DoctorRepository getRepository() {
        return repository;
    }
}
