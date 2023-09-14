package rocha.andre.api.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.appointment.Appointment;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.AppointmentRepository;
import rocha.andre.api.domain.appointment.AppointmentReturnDto;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorRepository;
import rocha.andre.api.domain.doctor.Specialty;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class AppointmentServiceImplTest {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentServiceImpl appointmentService;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("It should return a ScheduleAppointment Return DTO with valid info")
    void scheduleAppointmentScenario1() {
        //given
        var doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology, true);
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        var doctorSaved = doctorRepository.save(doctor);
        var patientSaved = patientRepository.save(patient);
        var timeInTwoHours = LocalDateTime.now().plusHours(2);

        var scheduledAppointment = new AppointmentDto(doctorSaved.getId(), patientSaved.getId(), timeInTwoHours, Specialty.cardiology);

        //when
        var result = appointmentService.scheduleAppointment(scheduledAppointment);

        //then
        assertNotNull(result);
        assertTrue(result instanceof AppointmentReturnDto);
        assertEquals(doctorSaved.getId(), result.doctor_id());
        assertEquals(patientSaved.getId(), result.patient_id());
        assertEquals(timeInTwoHours, scheduledAppointment.date());
    }

    @Test
    @DisplayName("It should return a validation Exception regarding appointment's time request time being under 30 min from " +
            "the intended scheduled appointment time")
    void scheduleAppointmentScenario2() {
        //given
        var doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology, true);
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        var doctorSaved = doctorRepository.save(doctor);
        var patientSaved = patientRepository.save(patient);
        var timeInTwentyMinutes = LocalDateTime.now().plusSeconds(1200);

        var scheduledAppointment = new AppointmentDto(doctorSaved.getId(), patientSaved.getId(), timeInTwentyMinutes, Specialty.cardiology);


        //when / then
        assertThrows(ValidationException.class, () -> {
            appointmentService.scheduleAppointment(scheduledAppointment);
        });
        assertNull(appointmentRepository.findByDoctorId(doctorSaved.getId()));
    }

    @Test
    @DisplayName("It should return a validation Exception regarding doctor already having an appointment at the request time")
    void scheduleAppointmentScenario3() {
        //given
        var doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology, true);
        var patient1 = createPatient("patient 1", "patient@email.com", "00000000011");
        var patient2 = createPatient("patient 2", "newpatient@email.com", "00000000022");
        var doctorSaved = doctorRepository.save(doctor);
        var patientSaved = patientRepository.save(patient1);
        var patientSaved2 = patientRepository.save(patient2);
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        var timeInTheFuture = LocalDateTime.parse("30/09/2023 15:00", formatter);


        var scheduledAppointment = new AppointmentDto(doctorSaved.getId(), patientSaved.getId(), timeInTheFuture, null);
        var result = appointmentService.scheduleAppointment(scheduledAppointment);

        var intendedAppointment = new AppointmentDto(doctorSaved.getId(), patientSaved2.getId(), timeInTheFuture, null);

        //when / then
        assertTrue(result instanceof AppointmentReturnDto);
        assertEquals(doctorSaved.getId(), result.doctor_id());

        assertThrows(ValidationException.class, () -> {
            appointmentService.scheduleAppointment(intendedAppointment);
        });

        long appointmentCount = appointmentRepository.countByDoctorId(doctorSaved.getId());
        assertEquals(1, appointmentCount);
    }

    @Test
    @DisplayName("It should cancel the scheduled appointment, ")

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

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty, Boolean active) {
        if (active == null) {
            active = false;
        }
        var doctor = new Doctor(dataDoctor(name, email, crm, specialty, active));
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        return patient;
    }
}
