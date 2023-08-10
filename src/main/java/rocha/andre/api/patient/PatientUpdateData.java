package rocha.andre.api.patient;

import rocha.andre.api.address.DataAddress;

public record PatientUpdateData(
        String name,
        String telephone,
        DataAddress address) {
}
