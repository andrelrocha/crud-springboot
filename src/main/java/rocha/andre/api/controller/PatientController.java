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
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientReturnDto;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientUpdateDto;
import rocha.andre.api.domain.patient.UseCase.*;
import rocha.andre.api.service.PatientService;

import java.net.URI;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<Page<PatientReturnDto>> getAllPatients(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        Page<PatientReturnDto> page = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientReturnDto> getPatientById(@PathVariable Long id) {
        PatientReturnDto patientById = patientService.getPatientById(id);
        return ResponseEntity.ok(patientById);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientReturnDto> createPatient(@RequestBody @Valid PatientDto data, UriComponentsBuilder uriBuilder) {
        PatientReturnDto newPatient = patientService.createPatient(data);
        URI uri = uriBuilder.path("/patients/{id}").buildAndExpand(newPatient.id()).toUri();
        return ResponseEntity.created(uri).body(newPatient);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<PatientReturnDto> updatePatient(@RequestBody @Valid PatientUpdateDto data, @PathVariable Long id) {
        PatientReturnDto updatedPatient = patientService.updatePatient(data, id);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}