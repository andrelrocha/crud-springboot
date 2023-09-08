package rocha.andre.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;
import rocha.andre.api.service.AppointmentService;

@RestController
@RequestMapping("/appointment")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    @Transactional
    public ResponseEntity scheduleAppointment(@RequestBody AppointmentDto data) {
        var dto = appointmentService.scheduleAppointment(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelAppointment(@RequestBody @Valid CancelAppointmentDto data) {
        appointmentService.cancelAppointment(data);
        return ResponseEntity.noContent().build();
    }
}
