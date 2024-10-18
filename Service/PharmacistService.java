package Service;

import Data.PharmacistRepository;
import Users.Pharmacist;

public class PharmacistService extends UserService<Pharmacist, PharmacistRepository> {
    PharmacistRepository repository;
    Pharmacist currentUser;

    public PharmacistService(String userId) {
        repository = new PharmacistRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.FindById(userId);
    }

    @Override
    public Pharmacist getCurrentUser() {
        return currentUser;
    }

    @Override
    public PharmacistRepository getRepository() {
        return repository;
    }
}
