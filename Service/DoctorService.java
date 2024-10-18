package Service;

import Data.DoctorRepository;
import Users.Doctor;

public class DoctorService extends UserService<Doctor, DoctorRepository> {
    DoctorRepository repository;
    Doctor currentUser;

    public DoctorService(String userId) {
        repository = new DoctorRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.FindById(userId);
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
