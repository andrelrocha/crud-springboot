package rocha.andre.api.domain.appointment.validations.schedulling;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;

import java.time.DayOfWeek;

@Component
public class ValidateWorkingHours implements ValidatorScheduleAppointments {

    public void validate(AppointmentDto data) {
        var appointmentDate = data.date();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeWorkingHours = appointmentDate.getHour() < 7;
        var afterWorkingHours = appointmentDate.getHour() > 18;

        if (sunday || beforeWorkingHours || afterWorkingHours) {
            throw new ValidationException("Appointment outside of the clinic's operating hours");
        }
    }
}
