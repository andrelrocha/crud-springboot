package rocha.andre.api.domain.appointment.validations;

import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;

import java.time.DayOfWeek;

public class ValidateWorkingHours {

    public void validateWorkingHours(AppointmentDto data) {
        var appointmentDate = data.date();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeWorkingHours = appointmentDate.getHour() < 7;
        var afterWorkingHours = appointmentDate.getHour() > 18;

        if (sunday || beforeWorkingHours || afterWorkingHours) {
            throw new ValidationException("Appointment outside of the clinic's operating hours");
        }


    }
}
