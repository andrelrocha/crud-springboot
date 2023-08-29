package rocha.andre.api.domain.appointment.validations.cancelling;

import rocha.andre.api.domain.appointment.CancelAppointmentDto;

public interface ValidatorCancelAppointment {
    void validate(CancelAppointmentDto data);
}
