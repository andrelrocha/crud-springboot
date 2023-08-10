package rocha.andre.api.domain.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientRepository;

@Component
public class DeletePatientUseCase {
    private PatientRepository repository;

    public DeletePatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public void deletePatient(Long id) {
            Patient patientToDelete = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with the provided ID does not exist."));

            patientToDelete.deactivate();
    }
}
