package rocha.andre.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rocha.andre.api.domain.appointment.AppointmentDetaillingData;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.UseCase.ScheduleAppointments;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private ScheduleAppointments scheduleAppointments;

    @PostMapping
    @Transactional
    public ResponseEntity scheduleAppointment(@RequestBody @Valid AppointmentDto data) {
        scheduleAppointments.schedule(data);
        return ResponseEntity.ok(new AppointmentDetaillingData(null, null, null, null));
    }
}
