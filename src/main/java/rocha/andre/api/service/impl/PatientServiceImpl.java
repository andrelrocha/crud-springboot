package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientReturnDto;
import rocha.andre.api.domain.patient.PatientUpdateDto;
import rocha.andre.api.domain.patient.UseCase.*;
import rocha.andre.api.service.PatientService;

public class PatientServiceImpl implements PatientService {
    @Autowired
    private CreatePatientUseCase createPatientUseCase;
    @Autowired
    private DeletePatientUseCase deletePatientUseCase;
    @Autowired
    private ListPatientsUseCase listPatientsUseCase;
    @Autowired
    private ListPatientByIdUseCase listPatientByIdUseCase;
    @Autowired
    private UpdatePatientUseCase updatePatientUseCase;

    @Override
    public Page<PatientReturnDto> getAllPatients(Pageable pageable) {
        var page = listPatientsUseCase.listPatient(pageable);
        return page;
    }

    @Override
    public PatientReturnDto getPatientById(Long id) {
        var patientById = listPatientByIdUseCase.listPatientById(id);
        return patientById;
    }

    @Override
    public PatientReturnDto createPatient(PatientDto data) {
        var newPatient = createPatientUseCase.createPatient(data);
        return newPatient;
    }

    @Override
    public PatientReturnDto updatePatient(PatientUpdateDto data, Long id) {
        var updatedPatient = updatePatientUseCase.updatePatient(data, id);
        return updatedPatient;
    }

    @Override
    public void deletePatient(Long id) {
        deletePatientUseCase.deletePatient(id);
    }
}
