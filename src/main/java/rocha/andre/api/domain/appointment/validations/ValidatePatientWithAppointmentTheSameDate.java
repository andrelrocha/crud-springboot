package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;

@Component
public class ValidatePatientWithAppointmentTheSameDate implements ValidatorScheduleAppointments {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(AppointmentDto data) {
        var firstHour = data.date().withHour(7);
        var lastHour = data.date().withHour(18);

        var patientHasAnotherAppointmentWithinTheSameDay = appointmentRepository.existsByPatientIdAndDateBetween(data.patientId(), firstHour, lastHour);

        if (patientHasAnotherAppointmentWithinTheSameDay) {
            throw new ValidationException("Patient already has another appointment in the same day.");
        }
    }
}
