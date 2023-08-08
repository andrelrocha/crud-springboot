package rocha.andre.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.doctor.DataDoctor;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void createDoctor(@RequestBody DataDoctor data) {
        System.out.println(data);

        System.out.println(data.name());
    }
}
