package rocha.andre.api.service;

import rocha.andre.api.domain.user.UserDTO;
import rocha.andre.api.domain.user.UserLoginDTO;
import rocha.andre.api.domain.user.UserResetPassDTO;
import rocha.andre.api.domain.user.UserReturnDTO;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserDTO data);
    UserReturnDTO createUser(UserDTO data);
    String forgotPassword(UserLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
