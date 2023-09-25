package rocha.andre.api.service;

import rocha.andre.api.domain.user.*;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserDto data);
    UserReturnDto createUser(UserDto data);
    String forgotPassword(UserLoginDTO data);
    String resetPassword(UserResetPassDTO data);
    String validateEmail(UserValidateEmail data);
}
