package rocha.andre.api.domain.user.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.user.*;
import rocha.andre.api.infra.utils.mail.GenerateMailToken;
import rocha.andre.api.infra.utils.mail.MailDTO;
import rocha.andre.api.infra.utils.mail.MailSenderMime;

import java.time.LocalDateTime;

@Component
public class CreateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MailSenderMime mailSender;
    @Autowired
    private GenerateMailToken mailToken;

    public UserReturnDto createUser(UserDto data) {
        boolean userExists = userRepository.userExistsByLogin(data.login());

        if (userExists) {
            throw new ValidationException("Email on user creation already exists in our database");
        }

        User newUser = new User(data);

        String encodedPassword = bCryptPasswordEncoder.encode(data.password());
        newUser.setPassword(encodedPassword);

        var userOnDb = userRepository.save(newUser);

        var oneHour = LocalDateTime.now().plusHours(1);
        var token = mailToken.generateEmailToken();

        userOnDb.setMailTokenConfirmation(token);
        userOnDb.setTokenExpiration(oneHour);

        var subject = "Validate email";
        var mailDTO = new MailDTO(subject, userOnDb.getLogin(), token);

        mailSender.sendMail(mailDTO);

        return new UserReturnDto(userOnDb);
    }
}
