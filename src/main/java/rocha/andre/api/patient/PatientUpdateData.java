package rocha.andre.api.patient;

import jakarta.validation.constraints.NotNull;
import rocha.andre.api.address.DataAddress;

public record PatientUpdateData(
        @NotNull
        Long id,
        String name,
        String telephone,
        DataAddress address) {
}
