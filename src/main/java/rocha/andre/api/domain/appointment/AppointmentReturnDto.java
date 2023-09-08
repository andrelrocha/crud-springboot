package rocha.andre.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentReturnDto(Long id,
                                   Long doctor_id,
                                   Long patient_id,
                                   LocalDateTime date) {
    public AppointmentReturnDto(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate());
    }
}
