package rocha.andre.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetaillingData(Long id,
                                        Long doctorId,
                                        Long patientId,
                                        LocalDateTime date) {
    public AppointmentDetaillingData(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate());
    }
}
