package rocha.andre.api.domain.appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.patient.Patient;

import java.time.LocalDateTime;
import java.util.Optional;

@Table(name = "appointments")
@Entity(name = "Appointment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private Patient patient;

    private LocalDateTime date;

    @Column(name = "cancelling_reason")
    @Enumerated(EnumType.STRING)
    private DeleteAppointmentReason deleteAppointmentReason;

    public void cancel(DeleteAppointmentReason reason) {
        this.deleteAppointmentReason = reason;
    }
}
