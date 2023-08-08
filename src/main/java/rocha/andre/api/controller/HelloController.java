package rocha.andre.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String helloWorld() {
        return "Im coming for you!";
    }
    //chegou uma req GET para /hello, chame a função helloWorld
}
