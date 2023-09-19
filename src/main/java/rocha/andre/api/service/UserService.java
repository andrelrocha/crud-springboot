package rocha.andre.api.service;

import rocha.andre.api.domain.user.UserDto;
import rocha.andre.api.domain.user.UserLoginDTO;
import rocha.andre.api.domain.user.UserResetPassDTO;
import rocha.andre.api.domain.user.UserReturnDto;
import rocha.andre.api.infra.security.TokenJwtDto;

public interface UserService {
    TokenJwtDto performLogin(UserDto data);
    UserReturnDto createUser(UserDto data);
    String forgotPassword(UserLoginDTO data);
    String resetPassword(UserResetPassDTO data);
}
