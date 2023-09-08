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
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.DoctorUpdateDTO;
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

    @GetMapping
    public ResponseEntity<Page<DoctorReturnDTO>> getAllDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<DoctorReturnDTO> page = doctorService.getAllDoctors(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorReturnDTO> getDoctorById(@PathVariable Long id) {
        DoctorReturnDTO doctorById = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctorById);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorReturnDTO> createDoctor(@RequestBody @Valid DoctorDTO data, UriComponentsBuilder uriBuilder) {
        DoctorReturnDTO newDoctor = doctorService.createDoctor(data);
        URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(newDoctor.id()).toUri();
        return ResponseEntity.created(uri).body(newDoctor);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorReturnDTO> updateDoctor(@RequestBody @Valid DoctorUpdateDTO data) {
        DoctorReturnDTO updatedDoctor = doctorService.updateDoctor(data);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
