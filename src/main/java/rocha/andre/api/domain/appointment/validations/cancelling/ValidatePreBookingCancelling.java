package rocha.andre.api.domain.appointment.validations.cancelling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentRepository;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidatePreBookingCancelling {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(CancelAppointmentDto data) {
        var schedule = appointmentRepository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var difference = Duration.between(now, schedule.getDate()).toHours();

        if (difference < 24) {
            throw new ValidationException("You can't cancel schedules within 24h before its scheduled time.");
        }
    }
}
