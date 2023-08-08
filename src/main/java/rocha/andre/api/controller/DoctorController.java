package rocha.andre.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.doctor.DataDoctor;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return repository.findAll();
    }

    @PostMapping
    @Transactional
    public void createDoctor(@RequestBody @Valid DataDoctor data) {
        repository.save(new Doctor(data));
    }
}
