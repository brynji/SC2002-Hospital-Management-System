package Service;

import Misc.RoleType;

public interface ILoginService {
    RoleType login(String userId, String password);
}
