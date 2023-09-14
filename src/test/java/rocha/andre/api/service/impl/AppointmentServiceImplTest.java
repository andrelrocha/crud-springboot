package rocha.andre.api.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.appointment.*;
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

    Doctor doctor;
    Patient patient;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        appointmentRepository.deleteAll();
        doctorRepository.deleteAll();
        patientRepository.deleteAll();

        doctor = createDoctor("doctor", "doctor@email.com", "123456", Specialty.cardiology, true);
        patient = createPatient("patient", "patient@email.com", "00000000011");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("It should return a ScheduleAppointment Return DTO with valid info")
    void scheduleAppointmentScenario1() {
        //given
        var timeInTwoHours = LocalDateTime.now().plusHours(2);

        var scheduledAppointment = new AppointmentDto(doctor.getId(), patient.getId(), timeInTwoHours, Specialty.cardiology);

        //when
        var result = appointmentService.scheduleAppointment(scheduledAppointment);

        //then
        assertNotNull(result);
        assertTrue(result instanceof AppointmentReturnDto);
        assertEquals(doctor.getId(), result.doctor_id());
        assertEquals(patient.getId(), result.patient_id());
        assertEquals(timeInTwoHours, scheduledAppointment.date());
    }

    @Test
    @DisplayName("It should return a validation Exception regarding appointment's time request time being under 30 min from " +
            "the intended scheduled appointment time")
    void scheduleAppointmentScenario2() {
        //given
        var timeInTwentyMinutes = LocalDateTime.now().plusSeconds(1200);

        var scheduledAppointment = new AppointmentDto(doctor.getId(), patient.getId(), timeInTwentyMinutes, Specialty.cardiology);

        //when / then
        assertThrows(ValidationException.class, () -> {
            appointmentService.scheduleAppointment(scheduledAppointment);
        });
        assertNull(appointmentRepository.findByDoctorId(doctor.getId()));
    }

    @Test
    @DisplayName("It should return a validation Exception regarding doctor already having an appointment at the request time")
    void scheduleAppointmentScenario3() {
        //given
        var patient2 = createPatient("patient 2", "newpatient@email.com", "00000000022");
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        var timeInTheFuture = LocalDateTime.parse("30/09/2023 15:00", formatter);

        var scheduledAppointment = new AppointmentDto(doctor.getId(), patient.getId(), timeInTheFuture, null);
        var result = appointmentService.scheduleAppointment(scheduledAppointment);

        var intendedAppointment = new AppointmentDto(doctor.getId(), patient2.getId(), timeInTheFuture, null);

        //when / then
        assertTrue(result instanceof AppointmentReturnDto);
        assertEquals(doctor.getId(), result.doctor_id());

        assertThrows(ValidationException.class, () -> {
            appointmentService.scheduleAppointment(intendedAppointment);
        });

        long appointmentCount = appointmentRepository.countByDoctorId(doctor.getId());
        assertEquals(1, appointmentCount);
    }

    @Test
    @DisplayName("It should cancel the scheduled appointment, adding the cancel reason to the appointment register")
    void cancelAppointmentScenario1() {
        //given
        var timeInFiftyHours = LocalDateTime.now().plusHours(50);

        var scheduledAppointment = new AppointmentDto(doctor.getId(), patient.getId(), timeInFiftyHours, null);
        var result = appointmentService.scheduleAppointment(scheduledAppointment);
        var cancelAppointmentDto = new CancelAppointmentDto(result.id(), CancelAppointmentReason.DOCTOR_CANCELLED);

        //when
        appointmentService.cancelAppointment(cancelAppointmentDto);

        //then
        var appointmentInDb = appointmentRepository.findByDoctorId(result.doctor_id());
        assertNotNull(appointmentInDb.getCancelAppointmentReason());
        assertEquals(CancelAppointmentReason.DOCTOR_CANCELLED, appointmentInDb.getCancelAppointmentReason());
    }

    @Test
    @DisplayName("It shouldn't cancel the scheduled appointment, throwing a exception for trying to cancel an appointment" +
            "in under 24h before the scheduled time")
    void cancelAppointmentScenario2() {
        //given
        var timeInTwoHours = LocalDateTime.now().plusHours(2);

        var scheduledAppointment = new AppointmentDto(doctor.getId(), patient.getId(), timeInTwoHours, null);
        var result = appointmentService.scheduleAppointment(scheduledAppointment);

        var cancelAppointmentDto = new CancelAppointmentDto(result.id(), CancelAppointmentReason.DOCTOR_CANCELLED);

        //when / then
        assertThrows(ValidationException.class, () -> {
            appointmentService.cancelAppointment(cancelAppointmentDto);
        });
        var appointmentInDb = appointmentRepository.findByDoctorId(result.doctor_id());
        assertNull(appointmentInDb.getCancelAppointmentReason());
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

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty, Boolean active) {
        if (active == null) {
            active = false;
        }
        var doctor = new Doctor(dataDoctor(name, email, crm, specialty, active));
        var savedDoctor = doctorRepository.save(doctor);
        return savedDoctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        var savedPatient = patientRepository.save(patient);
        return patient;
    }
}
