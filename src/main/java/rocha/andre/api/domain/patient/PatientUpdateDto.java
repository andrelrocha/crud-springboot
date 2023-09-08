package rocha.andre.api.domain.patient;

import rocha.andre.api.domain.address.DataAddress;

public record PatientUpdateDto(
        String name,
        String telephone,
        DataAddress address) {
}
