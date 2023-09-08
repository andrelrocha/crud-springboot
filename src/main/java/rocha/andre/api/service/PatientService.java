package rocha.andre.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientReturnDto;
import rocha.andre.api.domain.patient.PatientUpdateDto;

public interface PatientService {
    public Page<PatientReturnDto> getAllPatients(Pageable pageable);
    public PatientReturnDto getPatientById(Long id);
    public PatientReturnDto createPatient(PatientDto data);
    public PatientReturnDto updatePatient(PatientUpdateDto data, Long id);
    public void deletePatient(Long id);
}
