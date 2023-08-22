package rocha.andre.api.user;

public record AuthenticateDto(
        String login,
        String password
) {  }
