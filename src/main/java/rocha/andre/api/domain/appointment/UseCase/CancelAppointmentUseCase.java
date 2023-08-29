package rocha.andre.api.domain.appointment.UseCase;

import org.springframework.stereotype.Component;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.appointment.AppointmentRepository;
import rocha.andre.api.domain.appointment.DeleteAppointmentDto;

@Component
public class CancelAppointmentUseCase {

    private AppointmentRepository appointmentRepository;

    public void cancelAppointment(DeleteAppointmentDto data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new ValidationException("Appointment ID not found in our database");
        }

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.cancel(data.reason());
    }
}
