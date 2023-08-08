package rocha.andre.api.doctor;

import rocha.andre.api.address.DataAddress;

public record DataDoctor(String name, String email, String crm, Specialty specialty, DataAddress address) { }
