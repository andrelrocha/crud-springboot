package rocha.andre.api.domain.patient.UseCase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.PatientListingData;
import rocha.andre.api.domain.patient.PatientRepository;

@Component
public class ListPatientsUseCase {
    private PatientRepository repository;

    public ListPatientsUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public Page<PatientListingData> listDoctor(Pageable pagination) {
            return repository.findAllByActiveTrue(pagination).map(PatientListingData::new);
    }
}

