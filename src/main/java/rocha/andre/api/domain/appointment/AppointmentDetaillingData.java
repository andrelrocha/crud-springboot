package rocha.andre.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetaillingData(Long id,
                                        Long doctorId,
                                        Long patientId,
                                        LocalDateTime date) {
}
