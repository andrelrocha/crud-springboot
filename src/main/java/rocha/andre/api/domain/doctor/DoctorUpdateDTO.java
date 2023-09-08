package rocha.andre.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import rocha.andre.api.domain.address.DataAddress;

public record DoctorUpdateDTO(
        @NotNull
        Long id,
        String name,
        String telephone,
        DataAddress address) {
}
