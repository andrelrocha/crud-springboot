package rocha.andre.api.domain.appointment.validations;

import rocha.andre.api.domain.appointment.AppointmentDto;

public interface ValidatorScheduleAppointments {

    void validate(AppointmentDto data);
}
