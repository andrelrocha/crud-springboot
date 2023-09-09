package rocha.andre.api.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.patient.Patient;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.domain.patient.PatientRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class PatientServiceImplTest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientServiceImpl patientService;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        patientRepository.deleteAll();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllPatients() {
    }

    @Test
    void getPatientById() {
    }

    @Test
    void createPatient() {
    }

    @Test
    void updatePatient() {
    }

    @Test
    void deletePatient() {
        //given
        var patient = createPatient("patient", "patient@email.com", "00000000011");

        //when
        patientRepository.delete(patient);

        //then
        assertFalse(patientRepository.existsByEmail("patient@email.com"));
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

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        return patient;
    }
}