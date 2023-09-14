package rocha.andre.api.controller;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.doctor.Doctor;
import rocha.andre.api.domain.doctor.DoctorDTO;
import rocha.andre.api.domain.doctor.DoctorReturnDTO;
import rocha.andre.api.domain.doctor.Specialty;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.service.impl.DoctorServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DoctorDTO> doctorDTOJacksonTester;

    @Autowired
    private JacksonTester<DoctorReturnDTO> doctorReturnDTOJacksonTester;

    @MockBean
    private DoctorServiceImpl doctorService;

    Doctor doctor1;
    Doctor doctor2;

    @BeforeEach
    void setUp() {
        doctor1 = createDoctor("user1", "email1@email.com", "123456", Specialty.cardiology, true);
        doctor2 = createDoctor("novo user", "novo-email@email.com", "654321", Specialty.cardiology, true);
    }


    @Test
    @DisplayName("It should return code 403 when the route is called by a non logged user")
    void randomRequestWithNoValidToken() throws Exception {
        var response = mvc.perform(get("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }
    @Test
    @DisplayName("It should return code 200 and a valid body")
    @WithMockUser
    void getDoctorScenario1() throws Exception {
        //when
        var response = mvc.perform(get("/doctors"))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    @DisplayName("It should return code 400 when data provided for the api is invalid")
    @WithMockUser
    void createDoctorScenario1() throws Exception {
        var response = mvc.perform(post("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return code 201 when data provided for the api is valid")
    @WithMockUser
    void createDoctorScenario2() throws Exception {
        //given
        var doctorReturn = new DoctorReturnDTO(doctor1);
        when(doctorService.createDoctor(any())).thenReturn(doctorReturn);

        //when
        var response = mvc.perform(
                        post("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(doctorDTOJacksonTester.write(
                                        new DoctorDTO("user1", "email1@email.com", "61999999999", "123456", Specialty.cardiology, addressData(), true)
                                ).getJson())
                )
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
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
        return doctor;
    }
}
