package rocha.andre.api.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.user.UseCase.CreateUserUseCase;
import rocha.andre.api.domain.user.UseCase.ForgotPasswordUseCase;
import rocha.andre.api.domain.user.UseCase.PerformLoginUseCase;
import rocha.andre.api.domain.user.UseCase.ResetPasswordUseCase;
import rocha.andre.api.domain.user.UserDto;
import rocha.andre.api.domain.user.UserLoginDTO;
import rocha.andre.api.domain.user.UserResetPassDTO;
import rocha.andre.api.domain.user.UserReturnDto;
import rocha.andre.api.infra.security.TokenJwtDto;
import rocha.andre.api.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private ForgotPasswordUseCase forgotPasswordUseCase;
    @Autowired
    private PerformLoginUseCase performLoginUseCase;
    @Autowired
    private ResetPasswordUseCase resetPasswordUseCase;

    @Override
    public TokenJwtDto performLogin(UserDto data) {
        var tokenJwt = performLoginUseCase.performLogin(data);
        return tokenJwt;
    }

    @Override
    public UserReturnDto createUser(UserDto data) {
        var user = createUserUseCase.createUser(data);
        return user;
    }

    @Override
    public String forgotPassword(UserLoginDTO data) {
        forgotPasswordUseCase.forgotPassword(data);
        return "Email successfully sent!";
    }

    @Override
    public String resetPassword(UserResetPassDTO data) {
        resetPasswordUseCase.resetPassword(data);
        return "Email successfully updated!";
    }
}
