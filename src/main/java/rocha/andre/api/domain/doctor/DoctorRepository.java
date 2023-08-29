package rocha.andre.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);

    Page<Doctor> findAllByActiveTrue(Pageable pagination);

    @Query("""
            select m from Doctor m 
            where 
            m.active = true
            and 
            m.specialty = :specialty
            and
            m.id not in(
                select c.doctor.id from Appointment c
                where 
                c.date = :date
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctorAvailableAtTheDate(Specialty specialty, LocalDateTime date);

}
