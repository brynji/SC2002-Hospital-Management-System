package Service;

import Misc.RoleType;

public interface ILoginService {
    public RoleType login(String userId, String password);
}
