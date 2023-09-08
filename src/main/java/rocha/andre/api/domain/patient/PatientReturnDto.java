package rocha.andre.api.domain.patient;

public record PatientReturnDto(Long id, String name, String email, String cpf) {

    public PatientReturnDto(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
