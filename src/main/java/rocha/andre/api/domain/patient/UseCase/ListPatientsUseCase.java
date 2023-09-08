package rocha.andre.api.domain.patient.UseCase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.patient.PatientReturnDto;
import rocha.andre.api.domain.patient.PatientRepository;

@Component
public class ListPatientsUseCase {
    private PatientRepository repository;

    public ListPatientsUseCase(PatientRepository repository) {
        this.repository = repository;
    }

    public Page<PatientReturnDto> listPatient(Pageable pageable) {
            return repository.findAllByActiveTrue(pageable).map(PatientReturnDto::new);
    }
}

