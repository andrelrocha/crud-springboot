package rocha.andre.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record CancelAppointmentDto(
        @NotNull
        Long idAppointment,
        @NotNull
        CancelAppointmentReason reason
) {}
