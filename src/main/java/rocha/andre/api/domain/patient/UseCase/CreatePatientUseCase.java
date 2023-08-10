package rocha.andre.api.domain.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientRegistrationData;
import rocha.andre.api.domain.patient.PatientRepository;

@Component
public class CreatePatientUseCase {
    private PatientRepository repository;

    public CreatePatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient createPatient(PatientRegistrationData data) {
            boolean patientExists = repository.existsByEmail(data.email());

            if (!patientExists) {
                Patient newPatient = new Patient(data);
                repository.save(newPatient);
                return newPatient;
            } else {
                throw new IllegalArgumentException("Patient with the same email already exists.");
            }
    }
}
