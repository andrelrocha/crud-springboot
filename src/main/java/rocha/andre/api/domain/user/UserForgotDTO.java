package rocha.andre.api.domain.user;

import java.time.LocalDateTime;

public record UserForgotDTO(
        String tokenMail,
        LocalDateTime tokenExpiration)
{ }
