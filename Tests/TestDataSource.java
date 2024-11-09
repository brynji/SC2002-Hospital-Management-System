package Tests;

import Data.DataSource;
import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.User;

import java.util.Map;

public class TestDataSource implements DataSource {
    @Override
    public Map<String, Role> getRoles() {
        return Map.of();
    }

    @Override
    public Map<String, Appointment> getAppointments() {
        return Map.of();
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public Map<String, User> getAllUsersWithRole(RoleType role) {
        return Map.of();
    }

    @Override
    public void update() {

    }

    @Override
    public void save() {

    }
}
