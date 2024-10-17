package Misc;

import java.io.Serializable;

public class Role implements Serializable {
    String userId;
    RoleType role;

    public Role(String userId, RoleType role) {
        this.userId = userId;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }
}
