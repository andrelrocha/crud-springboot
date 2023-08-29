package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.doctor.DoctorRepository;

public class ValidateDoctorIsActive {

    @Autowired
    private DoctorRepository doctorRepository;

    public void validateDoctorIsActive(AppointmentDto data) {
        if (data.doctorId() == null) {
            return;
        }

        var doctorIsActive = doctorRepository.findActiveById(data.doctorId());

        if (!doctorIsActive) {
            throw new ValidationException("You can't schedule appointments with doctors who've been deleted");
        }
    }
}
