package rocha.andre.api.domain.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;


@Component
public class ListDoctorByIdUseCase {
    private DoctorRepository repository;

    public ListDoctorByIdUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public DoctorReturnDTO listDoctorById (Long id) {
        Doctor doctorById = repository.getReferenceById(id);

        if (doctorById == null) {
            throw new IllegalArgumentException("Doctor with id " + id + " not found in our database");
        }

        return new DoctorReturnDTO(doctorById);
    }
}
