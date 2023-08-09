package rocha.andre.api.doctor;

import org.springframework.stereotype.Component;

@Component
public class CreateDoctorUseCase {
    private DoctorRepository repository;

    public CreateDoctorUseCase(DoctorRepository repository) {
        this.repository = repository;
    }

    public void createDoctor(DataDoctor data) {
        boolean doctorExists = repository.existsByEmail(data.email());

        if (!doctorExists) {
            repository.save(new Doctor(data));
        } else {
            throw new IllegalArgumentException("Doctor with the same email already exists.");
        }
    }
}
