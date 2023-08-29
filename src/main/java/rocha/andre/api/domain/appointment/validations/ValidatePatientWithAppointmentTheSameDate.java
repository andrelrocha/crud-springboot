package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;

public class ValidatePatientWithAppointmentTheSameDate {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validatePatientWithAppointmentTheSameDate(AppointmentDto data) {
        var firstHour = data.date().withHour(7);
        var lastHour = data.date().withHour(18);

        var patientHasAnotherAppointmentWithinTheSameDay = appointmentRepository.existsByPatientIdAndDateBetween(data.patientId(), firstHour, lastHour);

        if (patientHasAnotherAppointmentWithinTheSameDay) {
            throw new ValidationException("Patient already has another appointment in the same day.");
        }
    }
}
