package rocha.andre.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentReturnDto;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;
import rocha.andre.api.domain.appointment.UseCase.CancelAppointmentUseCase;
import rocha.andre.api.domain.appointment.UseCase.ScheduleAppointmentsUseCase;
import rocha.andre.api.service.AppointmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private ScheduleAppointmentsUseCase scheduleAppointmentsUseCase;
    @Autowired
    private CancelAppointmentUseCase cancelAppointmentUseCase;

    @Override
    public AppointmentReturnDto scheduleAppointment(AppointmentDto data) {
        var appointment = scheduleAppointmentsUseCase.schedule(data);
        return appointment;
    }

    @Override
    public void cancelAppointment(CancelAppointmentDto data) {
        cancelAppointmentUseCase.cancel(data);
    }
}
