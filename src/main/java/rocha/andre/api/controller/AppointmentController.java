package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.appointment.AppointmentDetaillingData;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.DeleteAppointmentDto;
import rocha.andre.api.domain.appointment.UseCase.CancelAppointmentUseCase;
import rocha.andre.api.domain.appointment.UseCase.ScheduleAppointmentsUseCase;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private ScheduleAppointmentsUseCase scheduleAppointments;
    @Autowired
    private CancelAppointmentUseCase cancelAppointment;

    @PostMapping
    @Transactional
    public ResponseEntity scheduleAppointment(@RequestBody AppointmentDto data) {
        System.out.println("aqui ta funcionando");
        var dto = scheduleAppointments.schedule(data);
        System.out.println("aqui nao ta");
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelAppointment(@RequestBody @Valid DeleteAppointmentDto data) {
        cancelAppointment.cancelAppointment(data);
        return ResponseEntity.noContent().build();
    }
}
