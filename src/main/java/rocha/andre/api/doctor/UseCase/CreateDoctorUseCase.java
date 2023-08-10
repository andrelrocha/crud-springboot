package rocha.andre.api.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.DataDoctor;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;

@Component
public class CreateDoctorUseCase {
    private DoctorRepository repository;

    public CreateDoctorUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor createDoctor(DataDoctor data) {
        boolean doctorExists = repository.existsByEmail(data.email());

        if (!doctorExists) {
            Doctor newDoctor = new Doctor(data);
            repository.save(newDoctor);
            return newDoctor;
        } else {
            throw new IllegalArgumentException("Doctor with the same email already exists.");
        }
    }
}
