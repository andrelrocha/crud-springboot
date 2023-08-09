package rocha.andre.api.doctor.UseCase;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import rocha.andre.api.doctor.DataUpdateDoctor;
import rocha.andre.api.doctor.Doctor;
import rocha.andre.api.doctor.DoctorRepository;

@Component
public class UpdateDoctorUseCase {
    private DoctorRepository repository;

    public UpdateDoctorUseCase(DoctorRepository repository)  {
        this.repository = repository;
    }

    public void updateDoctor(DataUpdateDoctor data) {
        Doctor doctorToUpdate = repository.findById(data.id())
                .orElseThrow(() -> new IllegalArgumentException("Doctor with the provided ID does not exist."));

        doctorToUpdate.updateData(data);
    }
}
