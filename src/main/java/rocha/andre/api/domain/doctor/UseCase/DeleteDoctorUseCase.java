package rocha.andre.api.domain.doctor.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;

@Component
public class DeleteDoctorUseCase {
    @Autowired
    private DoctorRepository repository;

    public void deleteDoctor(Long id) {
            Doctor doctorToDelete = repository.getReferenceById(id);

            if (doctorToDelete == null) {
                throw new ValidationException("No doctor was found for the provided id");
            }

            doctorToDelete.exclude();
    }
}
