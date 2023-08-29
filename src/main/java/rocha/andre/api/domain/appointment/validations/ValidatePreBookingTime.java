package rocha.andre.api.domain.appointment.validations;

import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidatePreBookingTime {
    public void validatePreBookingTime(AppointmentDto data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var preBookingTime = Duration.between(now, appointmentDate).toMinutes();

        if (preBookingTime < 30) {
            throw new ValidationException("The appointment must be scheduled with a minimum advance notice of 30 minutes.");
        }
    }
}
