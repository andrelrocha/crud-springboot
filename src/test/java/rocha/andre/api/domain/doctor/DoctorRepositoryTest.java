package rocha.andre.api.domain.doctor;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.appointment.Appointment;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientRegistrationData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("It should return null when the only doctor in DB is not available")
    void chooseRandomDoctorAvailableAtTheDateFirstScenario() {
        //given
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology);
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        scheduleAppointment(doctor, patient, nextMondayAt10);

        //when
        var doctorAvailable = doctorRepository.chooseRandomDoctorAvailableAtTheDate(Specialty.cardiology, nextMondayAt10);

        //then
        assertThat(doctorAvailable).isNull();
    }

    @Test
    @DisplayName("It should return doctor when he's available")
    void chooseRandomDoctorAvailableAtTheDateSecondScenario() {
        //given
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);
        var doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology);

        //when
        var doctorAvailable = doctorRepository.chooseRandomDoctorAvailableAtTheDate(Specialty.cardiology, nextMondayAt10);

        //then
        assertThat(doctorAvailable).isEqualTo(doctor);
    }

    ////////////////////
    private DataDoctor dataDoctor(String name, String email, String crm, Specialty specialty) {
        return new DataDoctor(
                name,
                email,
                "61999999999",
                crm,
                specialty,
                addressData()
        );
    }

    private PatientRegistrationData patientData(String nome, String email, String cpf) {
        return new PatientRegistrationData(
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

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(dataDoctor(name, email, crm, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        em.persist(patient);
        return patient;
    }
}