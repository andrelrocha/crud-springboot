package rocha.andre.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.patient.PatientRepository;

public class ValidatePatientsIsActive {

    @Autowired
    private PatientRepository patientRepository;

    public void validatePatientIsActive(AppointmentDto data) {
        var patientIsActive = patientRepository.findActiveById(data.patientId());

        if (!patientIsActive) {
            throw new ValidationException("You cant schedule appointments after your account was deleted");
        }
    }
}
