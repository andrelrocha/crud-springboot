package rocha.andre.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import rocha.andre.api.domain.address.DataAddress;

public record DoctorDTO(
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String telephone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull //não é string, mas enum, logo não é notBlank
        Specialty specialty,
        @NotNull
        @Valid
        DataAddress address,
        Boolean active
) { }
