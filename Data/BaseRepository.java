package Data;

import Misc.Appointment;
import Misc.Inventory;
import Misc.Role;
import Misc.RoleType;
import Users.*;

import java.util.*;

public abstract class BaseRepository{
    protected final DataSource dataSource;

    public BaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(){
        dataSource.save();
    }

    public void update(){
        dataSource.update();
    }

    public <T> T findUserById(String userId, RoleType role){
        return (T) dataSource.getAllUsersWithRole(role).get(userId);
    }

    public <T> Collection<T> getAllUsersWithRole(RoleType role){
        return dataSource.getAllUsersWithRole(role).values().stream().map(x->(T)x).toList();
    }

    public Collection<Appointment> getAllAppointmentsFromIds(Collection<String> appointmentIds) {
        var apps = dataSource.getAppointments();
        return appointmentIds.stream().map(apps::get).toList();
    }

    protected RoleType getRoleTypeFromUser(User user) {
        return switch (user) {
            case Patient _ -> RoleType.Patient;
            case Doctor _ -> RoleType.Doctor;
            case Pharmacist _ -> RoleType.Pharmacist;
            case Administrator _ -> RoleType.Administrator;
            default -> RoleType.None;
        };
    }

    public String generateID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        // Generate first segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) : // Uppercase
                    ('a' + random.nextInt(26))); // Lowercase
            id.append(letter);
        }
        id.append("-");

        // Generate second segment (4 random letters, mixed case)
        for (int i = 0; i < 4; i++) {
            char letter = (char) (random.nextBoolean() ?
                    ('A' + random.nextInt(26)) :
                    ('a' + random.nextInt(26)));
            id.append(letter);
        }
        id.append("-");

        // Generate third and fourth segments (4 random digits each)
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                int digit = random.nextInt(10);
                id.append(digit);
            }
            if (j == 0) id.append("-");
        }

        return id.toString();
    }
}
