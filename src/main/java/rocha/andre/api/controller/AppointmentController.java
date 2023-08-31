package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;
import rocha.andre.api.domain.appointment.UseCase.CancelAppointmentUseCase;
import rocha.andre.api.domain.appointment.UseCase.ScheduleAppointmentsUseCase;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private ScheduleAppointmentsUseCase scheduleAppointments;
    @Autowired
    private CancelAppointmentUseCase cancelAppointments;

    @PostMapping
    @Transactional
    public ResponseEntity scheduleAppointment(@RequestBody AppointmentDto data) {
        var dto = scheduleAppointments.schedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelAppointment(@RequestBody @Valid CancelAppointmentDto data) {
        cancelAppointments.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
