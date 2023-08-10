package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import rocha.andre.api.doctor.DataUpdateDoctor;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.patient.Patient;
import rocha.andre.api.patient.PatientListingData;
import rocha.andre.api.patient.PatientRegistrationData;
import rocha.andre.api.patient.PatientUpdateData;
import rocha.andre.api.patient.UseCase.*;

import java.net.URI;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private CreatePatientUseCase createPatientUseCase;
    @Autowired
    private ListPatientsUseCase listPatientsUseCase;
    @Autowired
    private ListPatientByIdUseCase listPatientByIdUseCase;
    @Autowired
    private UpdatePatientUseCase updatePatientUseCase;

    @GetMapping
    public ResponseEntity<Page<PatientListingData>> getAllPatients(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<PatientListingData> page = listPatientsUseCase.listDoctor(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patientById = listPatientByIdUseCase.listPatientById(id);
        return ResponseEntity.ok(patientById);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Patient> createPatient(@RequestBody @Valid PatientRegistrationData data, UriComponentsBuilder uriBuilder) {
        Patient newPatient = createPatientUseCase.createPatient(data);
        URI uri = uriBuilder.path("/patients/{id}").buildAndExpand(newPatient.getId()).toUri();
        return ResponseEntity.created(uri).body(newPatient);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Patient> updatePatient(@RequestBody @Valid PatientUpdateData data, @PathVariable Long id) {
        Patient updatedPatient = updatePatientUseCase.updatePatient(data, id);
        return ResponseEntity.ok(updatedPatient);
    }
}