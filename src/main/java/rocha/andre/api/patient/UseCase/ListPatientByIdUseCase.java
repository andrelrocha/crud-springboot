package rocha.andre.api.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.patient.Patient;
import rocha.andre.api.patient.PatientRepository;

@Component
public class ListPatientByIdUseCase {
    private PatientRepository repository;

    public ListPatientByIdUseCase (PatientRepository repository) {
        this.repository = repository;
    }

    public Patient listPatientById (Long id) {
        try {
            Patient patientById = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with id " + id + " not found in our database"));

            return patientById;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while retrieving a patient by ID");
        }
    }
}
