package rocha.andre.api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import rocha.andre.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentDto(Long doctor_id,
                             @NotNull
                             Long patient_id,
                             @NotNull
                             @Future
                             @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
                             LocalDateTime date,

                             Specialty specialty) {
}
