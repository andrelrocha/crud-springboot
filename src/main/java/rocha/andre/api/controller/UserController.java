package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.user.UserDto;
import rocha.andre.api.domain.user.UseCase.CreateUserUseCase;
import rocha.andre.api.domain.user.UseCase.PerformLoginUseCase;
import rocha.andre.api.domain.user.UserReturnDto;
import rocha.andre.api.infra.security.TokenJwtDto;

@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private PerformLoginUseCase performLoginUseCase;

    @Autowired
    private CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity performLogin(@RequestBody @Valid UserDto data) {
        TokenJwtDto tokenJwt = performLoginUseCase.performLogin(data);

        return ResponseEntity.ok(tokenJwt);
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody @Valid UserDto data) {
        UserReturnDto newUser = createUserUseCase.createUser(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
