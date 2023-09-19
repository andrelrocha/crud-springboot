package rocha.andre.api.domain.user;

public record UserReturnDTO(Long id,
                            String login) {

    public UserReturnDTO(User user) {
        this(user.getId(), user.getLogin());
    }
}
