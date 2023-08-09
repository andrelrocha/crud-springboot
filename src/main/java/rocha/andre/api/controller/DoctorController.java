package rocha.andre.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.doctor.DataDoctor;
import rocha.andre.api.doctor.DataListDoctor;
import rocha.andre.api.doctor.DataUpdateDoctor;
import rocha.andre.api.doctor.UseCase.CreateDoctorUseCase;
import rocha.andre.api.doctor.UseCase.DeleteDoctorUseCase;
import rocha.andre.api.doctor.UseCase.ListDoctorUseCase;
import rocha.andre.api.doctor.UseCase.UpdateDoctorUseCase;

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
    private UpdateDoctorUseCase updateDoctorUseCase;

    @GetMapping
    public Page<DataListDoctor> getAllDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        return listDoctorUseCase.listDoctor(pagination);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> createDoctor(@RequestBody @Valid DataDoctor data) {
        createDoctorUseCase.createDoctor(data);
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor with the name " + data.name() + " created");
    }

    @PutMapping
    @Transactional
    public ResponseEntity<String> updateDoctor(@RequestBody @Valid DataUpdateDoctor data) {
        updateDoctorUseCase.updateDoctor(data);
        return ResponseEntity.ok("Doctor with id " + data.id() + " successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        deleteDoctorUseCase.deleteDoctor(id);
    }
}
