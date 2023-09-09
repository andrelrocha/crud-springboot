package rocha.andre.api.domain.doctor;

public record DoctorReturnDTO(Long id, String name, String email, String crm, Specialty specialty, Boolean active) {
    public DoctorReturnDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty(), doctor.getActive());
    }
}
