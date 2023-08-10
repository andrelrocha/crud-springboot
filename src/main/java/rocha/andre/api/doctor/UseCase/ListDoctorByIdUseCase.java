package rocha.andre.api.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;


@Component
public class ListDoctorByIdUseCase {
    private DoctorRepository repository;

    public ListDoctorByIdUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor listDoctorById (Long id) {
        try {
            Doctor doctorById = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Doctor with id " + id + " not found in our database"));

            return doctorById;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while listing a doctor by ID");
        }
    }
}
