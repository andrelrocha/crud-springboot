package rocha.andre.api.domain.doctor.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.DoctorUpdateDTO;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorRepository;

@Component
public class UpdateDoctorUseCase {
    private DoctorRepository repository;

    public UpdateDoctorUseCase(DoctorRepository repository)  {
        this.repository = repository;
    }

    public DoctorReturnDTO updateDoctor(DoctorUpdateDTO data) {
            Doctor doctorToUpdate = repository.findById(data.id())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor with the provided ID does not exist."));

            doctorToUpdate.updateData(data);

            return new DoctorReturnDTO(doctorToUpdate);
    }
}
