package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.user.AuthenticateDto;

@Component
public class PerformLoginUseCase {

    @Autowired
    private AuthenticationManager manager;

    public void performLogin(AuthenticateDto data) {
        var token = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        //está chamando authenticateService
        Authentication authentication = manager.authenticate(token);


    }
}
