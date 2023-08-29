package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;

@Component
public class ValidateDoctorAlreadyScheduled implements ValidatorScheduleAppointments {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate (AppointmentDto data) {
        var doctorIsAlreadyScheduled = appointmentRepository.existsByDoctorIdAndDate(data.doctorId(), data.date());

        if (doctorIsAlreadyScheduled) {
            throw new ValidationException("Doctor is already scheduled for the date and time requested.");
        }
    }
}
