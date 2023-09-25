package rocha.andre.api.domain.user;


import jakarta.validation.constraints.NotNull;

public record UserValidateEmail(
        @NotNull
        String login,
        @NotNull
        String password,
        @NotNull
        String tokenConfirmation
) {
}
