package rocha.andre.api.domain.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.doctor.DataDoctor;
import rocha.andre.api.domain.doctor.DataListDoctor;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;

@Component
public class CreateDoctorUseCase {
    private DoctorRepository repository;

    public CreateDoctorUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public DataListDoctor createDoctor(DataDoctor data) {
            boolean doctorExists = repository.existsByEmail(data.email());

            if (!doctorExists) {
                Doctor newDoctor = new Doctor(data);
                repository.save(newDoctor);
                return new DataListDoctor(newDoctor);
            } else {
                throw new IllegalArgumentException("Doctor with the same email already exists.");
            }
    }
}
