package rocha.andre.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndDate(Long doctor_id, LocalDateTime date);

    boolean existsByPatientIdAndDateBetween(Long patient_id, LocalDateTime firstHour, LocalDateTime lastHour);

    Appointment findByDoctorId(Long doctor_id);

    Long countByDoctorId (Long doctor_id);
}
