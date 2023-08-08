package rocha.andre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.address.Address;
import rocha.andre.api.doctor.DataDoctor;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void createDoctor(@RequestBody DataDoctor data) {
        repository.save(new Doctor(data));
    }
}
