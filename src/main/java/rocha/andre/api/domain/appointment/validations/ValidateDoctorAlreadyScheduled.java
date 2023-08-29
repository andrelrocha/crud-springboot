package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;

public class ValidateDoctorAlreadyScheduled {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validateDoctorAlreadyScheduled (AppointmentDto data) {
        var doctorIsAlreadyScheduled = appointmentRepository.existsByDoctorIdAndDate(data.doctorId(), data.date());

        if (doctorIsAlreadyScheduled) {
            throw new ValidationException("Doctor is already scheduled for the date and time requested.");
        }
    }
}
