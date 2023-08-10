package rocha.andre.api.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.patient.Patient;
import rocha.andre.api.patient.PatientRepository;

@Component
public class DeletePatientUseCase {
    private PatientRepository repository;

    public DeletePatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public void deletePatient(Long id) {
        try {
            Patient patientToDelete = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with the provided ID does not exist."));

            patientToDelete.deactivate();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while deleting a patient");
        }
    }
}
