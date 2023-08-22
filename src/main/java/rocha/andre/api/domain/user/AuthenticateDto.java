package rocha.andre.api.domain.user;

public record AuthenticateDto(
        String login,
        String password
) {  }
