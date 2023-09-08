package rocha.andre.api.service;

import rocha.andre.api.domain.appointment.AppointmentReturnDto;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;

public interface AppointmentService {
    public AppointmentReturnDto scheduleAppointment(AppointmentDto data);
    public void cancelAppointment(CancelAppointmentDto data);
}
