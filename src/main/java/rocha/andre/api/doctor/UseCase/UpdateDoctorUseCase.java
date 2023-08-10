package rocha.andre.api.doctor.UseCase;

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

    public Doctor updateDoctor(DataUpdateDoctor data) {
        try {
            Doctor doctorToUpdate = repository.findById(data.id())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor with the provided ID does not exist."));

            doctorToUpdate.updateData(data);

            return doctorToUpdate;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while updating a doctor");
        }
    }
}
