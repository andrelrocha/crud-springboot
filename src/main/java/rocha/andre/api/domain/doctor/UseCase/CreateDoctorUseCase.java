package rocha.andre.api.domain.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;

@Component
public class CreateDoctorUseCase {
    private DoctorRepository repository;

    public CreateDoctorUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public DoctorReturnDTO createDoctor(DoctorDTO data) {
            boolean doctorExists = repository.existsByEmail(data.email());

            if (!doctorExists) {
                Doctor newDoctor = new Doctor(data);
                repository.save(newDoctor);
                return new DoctorReturnDTO(newDoctor);
            } else {
                throw new IllegalArgumentException("Doctor with the same email already exists.");
            }
    }
}
