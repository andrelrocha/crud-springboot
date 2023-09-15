package rocha.andre.api.domain.appointment;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorRepository;
import rocha.andre.api.domain.doctor.Specialty;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("It should return true if there's a doctor with an appointment at the requested date")
    void existsByDoctorIdAndDateScenario1() {
        //given
        var doctor = createDoctor("doctor", "firstdoctoremail@email.com", "654321", Specialty.cardiology, true);
        var patient = createPatient("patient", "nevercreated@email.com", "00000022011");
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        scheduleAppointment(doctor, patient, nextMondayAt10);

        //when
        var doctorWithAnAppointmentAtTheRequestedTime = appointmentRepository.existsByDoctorIdAndDate(doctor.getId(), nextMondayAt10);


        //then
        assertTrue(doctorWithAnAppointmentAtTheRequestedTime);
    }

    @Test
    @DisplayName("It should return false, since the doctor doesn't have any appointments at the requested date")
    void existsByDoctorIdAndDateScenario2() {
        //given
        var doctor = createDoctor("doctor", "firstdoctoremail@email.com", "654321", Specialty.cardiology, true);
        var patient = createPatient("patient", "nevercreated@email.com", "00000022011");
        var now = LocalDateTime.now();
        scheduleAppointment(doctor, patient, now);

        //when
        var doctorWithAnAppointmentAtTheRequestedTime = appointmentRepository.existsByDoctorIdAndDate(doctor.getId(), now.plusHours(2));


        //then
        assertFalse(doctorWithAnAppointmentAtTheRequestedTime);
    }

    @Test
    @DisplayName("It should return true, since the patient has an appointment scheduled for the requested period")
    void existsByPatientIdAndDateBetweenScenario1() {
        //given
        var doctor = createDoctor("doctor", "firstdoctoremail@email.com", "654321", Specialty.cardiology, true);
        var patient = createPatient("patient", "nevercreated@email.com", "00000022011");
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        scheduleAppointment(doctor, patient, nextMondayAt10.minusHours(2));
        var now = LocalDateTime.now();

        //when
        var patientWithAnAppointmentAtTheRequestedPeriod = appointmentRepository.existsByPatientIdAndDateBetween(patient.getId(), now, nextMondayAt10);

        //then
        assertTrue(patientWithAnAppointmentAtTheRequestedPeriod);
    }

    @Test
    @DisplayName("It should return false, since the patient hasn't an appointment scheduled for the requested period")
    void existsByPatientIdAndDateBetweenScenario2() {
        //given
        var doctor = createDoctor("doctor", "firstdoctoremail@email.com", "654321", Specialty.cardiology, true);
        var patient = createPatient("patient", "nevercreated@email.com", "00000022011");
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        scheduleAppointment(doctor, patient, nextMondayAt10.plusHours(2));
        var now = LocalDateTime.now();

        //when
        var patientWithAnAppointmentAtTheRequestedPeriod = appointmentRepository.existsByPatientIdAndDateBetween(patient.getId(), now, nextMondayAt10);

        //then
        assertFalse(patientWithAnAppointmentAtTheRequestedPeriod);
    }

    ////////////////////
    private DoctorDTO dataDoctor(String name, String email, String crm, Specialty specialty, Boolean active) {
        return new DoctorDTO(
                name,
                email,
                "61999999999",
                crm,
                specialty,
                addressData(),
                active
        );
    }

    private PatientDto patientData(String nome, String email, String cpf) {
        return new PatientDto(
                nome,
                email,
                "61999999999",
                cpf,
                addressData()
        );
    }

    private DataAddress addressData() {
        return new DataAddress(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

    private void scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Appointment(null, doctor, patient, date, null));
    }

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty, Boolean active) {
        if (active == null) {
            active = false;
        }
        var doctor = new Doctor(dataDoctor(name, email, crm, specialty, active));
        em.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        em.persist(patient);
        return patient;
    }
}