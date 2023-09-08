package rocha.andre.api.domain.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientRepository;
import rocha.andre.api.domain.patient.PatientReturnDto;

@Component
public class CreatePatientUseCase {
    private PatientRepository repository;

    public CreatePatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public PatientReturnDto createPatient(PatientDto data) {
            boolean patientExists = repository.existsByEmail(data.email());

            if (!patientExists) {
                Patient newPatient = new Patient(data);
                repository.save(newPatient);
                return new PatientReturnDto(newPatient);
            } else {
                throw new IllegalArgumentException("Patient with the same email already exists.");
            }
    }
}
