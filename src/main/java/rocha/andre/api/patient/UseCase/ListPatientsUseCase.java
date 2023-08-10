package rocha.andre.api.patient.UseCase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.patient.PatientListingData;
import rocha.andre.api.patient.PatientRepository;

@Component
public class ListPatientsUseCase {
    private PatientRepository repository;

    public ListPatientsUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public Page<PatientListingData> listDoctor(Pageable pagination) {
        try {
            return repository.findAllByActiveTrue(pagination).map(PatientListingData::new);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while listing patients");
        }
    }
}

