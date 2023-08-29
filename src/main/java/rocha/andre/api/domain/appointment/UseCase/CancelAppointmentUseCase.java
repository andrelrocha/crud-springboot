package rocha.andre.api.domain.appointment.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentRepository;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;
import rocha.andre.api.domain.appointment.validations.cancelling.ValidatePreBookingCancelling;


@Service
public class CancelAppointmentUseCase {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    ValidatePreBookingCancelling validatePreBookingCancelling;

    public void cancel(CancelAppointmentDto data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new ValidationException("Appointment ID not found in our database");
        }

        validatePreBookingCancelling.validate(data);

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }
}
