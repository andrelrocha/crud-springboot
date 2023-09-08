package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import rocha.andre.api.domain.doctor.DataDoctor;
import rocha.andre.api.domain.doctor.DataListDoctor;
import rocha.andre.api.domain.doctor.DataUpdateDoctor;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.UseCase.*;
import rocha.andre.api.service.DoctorService;

import java.net.URI;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private CreateDoctorUseCase createDoctorUseCase;
    @Autowired
    private DeleteDoctorUseCase deleteDoctorUseCase;
    @Autowired
    private ListDoctorByIdUseCase listDoctorByIdUseCase;
    @Autowired
    private UpdateDoctorUseCase updateDoctorUseCase;

    @GetMapping
    public ResponseEntity<Page<DataListDoctor>> getAllDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<DataListDoctor> page = doctorService.getAllDoctors(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        Doctor doctorById = listDoctorByIdUseCase.listDoctorById(id);
        return ResponseEntity.ok(doctorById);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DataListDoctor> createDoctor(@RequestBody @Valid DataDoctor data, UriComponentsBuilder uriBuilder) {
        DataListDoctor newDoctor = createDoctorUseCase.createDoctor(data);
        URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(newDoctor.id()).toUri();
        return ResponseEntity.created(uri).body(newDoctor);
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
