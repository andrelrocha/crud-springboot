package rocha.andre.api.doctor;

import jakarta.validation.constraints.NotNull;
import rocha.andre.api.address.Address;
import rocha.andre.api.address.DataAddress;

public record DataUpdateDoctor(
        @NotNull
        Long id,
        String name,
        String telephone,
        DataAddress address) {
}
