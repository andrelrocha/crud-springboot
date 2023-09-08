package rocha.andre.api.domain.patient.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientRepository;
import rocha.andre.api.domain.patient.PatientReturnDto;

@Component
public class ListPatientByIdUseCase {
    private PatientRepository repository;

    public ListPatientByIdUseCase (PatientRepository repository) {
        this.repository = repository;
    }

    public PatientReturnDto listPatientById (Long id) {
            Patient patientById = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Patient with id " + id + " not found in our database"));

            return new PatientReturnDto(patientById);
    }
}
