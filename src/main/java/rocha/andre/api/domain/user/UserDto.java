package rocha.andre.api.domain.user;

import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull
        String login,
        @NotNull
        String password
) {  }
