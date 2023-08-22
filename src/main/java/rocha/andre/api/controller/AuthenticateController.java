package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.user.AuthenticateDto;

@RestController
@RequestMapping("/login")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity performLogin(@RequestBody @Valid AuthenticateDto data) {
        var token = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        //está chamando authenticateService
        Authentication authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
