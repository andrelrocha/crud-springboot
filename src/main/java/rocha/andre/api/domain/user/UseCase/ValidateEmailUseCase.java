package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.user.UserRepository;
import rocha.andre.api.domain.user.UserValidateEmail;

import java.time.LocalDateTime;

@Component
public class ValidateEmailUseCase {
    @Autowired
    private UserRepository repository;

    public void validateEmail(UserValidateEmail data) {
        var user = repository.findByLoginToHandle(data.login());

        if (user == null) {
            throw new ValidationException("No user was found for the provided login");
        }

        var tokenExpected = user.getTokenConfirmation();
        var tokenExpiration = user.getTokenExpiration();
        var now = LocalDateTime.now();

        var tokenIsValid = tokenExpected.equals(data.tokenConfirmation()) && now.isBefore(tokenExpiration);

        if (tokenIsValid) {
            user.setValidated();
        } else {
            throw new ValidationException("Your token is not valid");
        }
    }
}
