package rocha.andre.api.domain.user;

public record UserReturnDto(Long id,
                            String login) {

    public UserReturnDto(User user) {
        this(user.getId(), user.getLogin());
    }
}
