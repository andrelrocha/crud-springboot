package rocha.andre.api.domain.patient;

public record PatientListingData(Long id, String name, String email, String cpf) {

    public PatientListingData(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
