package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.patient.PatientRepository;

@Component
public class ValidatePatientsIsActive implements ValidatorScheduleAppointments {

    @Autowired
    private PatientRepository patientRepository;

    public void validate(AppointmentDto data) {
        var patientIsActive = patientRepository.findActiveById(data.patientId());

        if (!patientIsActive) {
            throw new ValidationException("You cant schedule appointments after your account was deleted");
        }
    }
}
