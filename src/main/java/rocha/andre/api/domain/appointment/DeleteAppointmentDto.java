package rocha.andre.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record DeleteAppointmentDto (
        @NotNull
        Long idAppointment,
        @NotNull
        DeleteAppointmentReason reason
) {}
