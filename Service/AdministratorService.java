package Service;

import Data.AdministratorRepository;
import Users.Administrator;

public class AdministratorService extends UserService<Administrator, AdministratorRepository> {
    AdministratorRepository repository;
    Administrator currentUser;

    public AdministratorService(String userId) {
        repository = new AdministratorRepository();
        setCurrentUser(userId);
    }

    @Override
    public void setCurrentUser(String userId) {
        currentUser = repository.FindById(userId);
    }

    @Override
    public Administrator getCurrentUser() {
        return currentUser;
    }

    @Override
    public AdministratorRepository getRepository() {
        return repository;
    }
}
