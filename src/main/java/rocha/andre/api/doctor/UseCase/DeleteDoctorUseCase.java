package rocha.andre.api.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;

@Component
public class DeleteDoctorUseCase {
    private DoctorRepository repository;

    public DeleteDoctorUseCase(DoctorRepository repository)  {
        this.repository = repository;
    }

    public void deleteDoctor(Long id) {
        Doctor doctorToDelete = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor with the provided ID does not exist."));

        repository.delete(doctorToDelete);
    }
}
