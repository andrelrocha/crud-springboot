package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.doctor.*;
import rocha.andre.api.doctor.UseCase.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;
    @Autowired
    private DeleteDoctorUseCase deleteDoctorUseCase;
    @Autowired
    private ListDoctorUseCase listDoctorUseCase;
    @Autowired
    private ListDoctorByIdUseCase listDoctorByIdUseCase;
    @Autowired
    private UpdateDoctorUseCase updateDoctorUseCase;

    @GetMapping
    public ResponseEntity<Page<DataListDoctor>> getAllDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<DataListDoctor> page = listDoctorUseCase.listDoctor(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return listDoctorByIdUseCase.listDoctorById(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> createDoctor(@RequestBody @Valid DataDoctor data) {
        createDoctorUseCase.createDoctor(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor with the name " + data.name() + " created");
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Doctor> updateDoctor(@RequestBody @Valid DataUpdateDoctor data) {
        Doctor updatedDoctor = updateDoctorUseCase.updateDoctor(data);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDoctor(@PathVariable Long id) {
        deleteDoctorUseCase.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
