package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.user.AuthenticateDto;
import rocha.andre.api.domain.user.UseCase.PerformLoginUseCase;
import rocha.andre.api.infra.security.TokenJwtDto;

@RestController
@RequestMapping("/login")
public class AuthenticateController {

    @Autowired
    PerformLoginUseCase performLoginUseCase;


    @PostMapping
    public ResponseEntity performLogin(@RequestBody @Valid AuthenticateDto data) {
        TokenJwtDto tokenJwt = performLoginUseCase.performLogin(data);

        return ResponseEntity.ok(tokenJwt);
    }
}
