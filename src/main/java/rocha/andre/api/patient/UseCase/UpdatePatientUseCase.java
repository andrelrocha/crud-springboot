package rocha.andre.api.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.patient.Patient;
import rocha.andre.api.patient.PatientRepository;
import rocha.andre.api.patient.PatientUpdateData;

@Component
public class UpdatePatientUseCase {
    private PatientRepository repository;

    public UpdatePatientUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient updatePatient(PatientUpdateData data, Long id) {
        Patient patientToUpdate = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient with the provided ID does not exist."));

        patientToUpdate.updateInformation(data);

        return patientToUpdate;
    }
}
