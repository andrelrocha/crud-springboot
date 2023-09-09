package rocha.andre.api.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.DoctorUpdateDTO;
import rocha.andre.api.domain.doctor.Specialty;
import rocha.andre.api.domain.patient.*;

import java.io.IOException;

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
    @DisplayName("It should return a page of patients")
    void getAllPatientsScenario1() {
        //given
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        patientRepository.save(patient);

        Sort descendingSortByEmail = Sort.by(Sort.Order.desc("email"));
        Pageable pageable = PageRequest.of(0, 10, descendingSortByEmail);

        //when
        var result = patientService.getAllPatients(pageable);

        //then
        assertNotNull(result);
        assertTrue(result instanceof Page);

        boolean patientFoundInResult = false;
        for (PatientReturnDto p : result) {
            if (p.id().equals(patient.getId()) &&
                    p.name().equals(patient.getName()) &&
                    p.email().equals(patient.getEmail()) &&
                    p.cpf().equals(patient.getCpf())) {
                patientFoundInResult = true;
                break;
            }
        }
        assertTrue(patientFoundInResult);
    }

    @Test
    @DisplayName("It should return all patients, following the pattern provided")
    void getAllPatientsScenario2() {
        //given
        var patient1 = createPatient("patient", "zapatient@email.com", "00000000011");
        var patient2 = createPatient("patient 2", "aapatient@email.com", "00003300011");
        patientRepository.save(patient1);
        patientRepository.save(patient2);


        Sort ascendingSortByEmail = Sort.by(Sort.Order.asc("email"));
        Pageable pageable = PageRequest.of(0, 10, ascendingSortByEmail);

        //when
        var result = patientService.getAllPatients(pageable);

        //then
        assertNotNull(result);
        assertTrue(result instanceof Page);

        String email1 = result.getContent().get(0).email();
        String email2 = result.getContent().get(1).email();
        assertTrue(email1.compareTo(email2) <= 0);
    }

    @Test
    void getPatientById() {
        //given
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        var savedPatient = patientRepository.save(patient);

        //when
        var patientById = patientService.getPatientById(savedPatient.getId());

        //then
        assertEquals(savedPatient.getName(), patientById.name());
    }

    @Test
    @DisplayName("It should create a patient entity on db")
    void createPatient() {
        //given
        var patient = patientData("patient", "patient@email.com", "00000000011");

        //when
        patientService.createPatient(patient);
        var patientCreated = patientRepository.findByName(patient.name());

        //then
        assertEquals(patient.name(), patientCreated.getName());
    }

    @Test
    @DisplayName("It should update patient's info based on its id and the UpdateDTO")
    void updatePatient() throws IOException {
        //given
        var patient = createPatient("patient", "patient@email.com", "00000000011");
        var patientSaved = patientRepository.save(patient);
        var newName = "new patient";
        var updatedInfo = new PatientUpdateDto(newName, "85912121212", addressData());

        //when
        patientService.updatePatient(updatedInfo, patientSaved.getId());

        var updatedPatient = patientRepository.findById(patientSaved.getId()).orElse(null);

        //then
        assertNotNull(updatedPatient);
        assertNotEquals(patient.getName(), updatedPatient.getName());
    }

    @Test
    @DisplayName("It should hard delete patient's info based on its id")
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