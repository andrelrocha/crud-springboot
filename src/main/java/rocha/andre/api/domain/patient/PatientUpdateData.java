package rocha.andre.api.domain.patient;

import rocha.andre.api.domain.address.DataAddress;

public record PatientUpdateData(
        String name,
        String telephone,
        DataAddress address) {
}
