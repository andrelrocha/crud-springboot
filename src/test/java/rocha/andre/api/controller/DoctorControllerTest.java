package rocha.andre.api.controller;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import rocha.andre.api.domain.ValidationException;
import rocha.andre.api.domain.address.DataAddress;
import rocha.andre.api.domain.doctor.*;
import rocha.andre.api.domain.doctor.UseCase.DeleteDoctorUseCase;
import rocha.andre.api.domain.doctor.UseCase.ListDoctorByIdUseCase;
import rocha.andre.api.domain.patient.PatientDto;
import rocha.andre.api.service.DoctorService;
import rocha.andre.api.service.impl.DoctorServiceImpl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    @Autowired
    private JacksonTester<DoctorUpdateDTO> doctorUpdateDTOJacksonTester;


    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorRepository repository;


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
    void getDoctorByIdScenario1() throws Exception {
        //given
        when(doctorService.getDoctorById(any())).thenReturn(new DoctorReturnDTO(doctor1));

        //when
        var response = mvc.perform(get("/doctors/1"))
                .andReturn().getResponse();

        var expectedJson = doctorReturnDTOJacksonTester.write(
                new DoctorReturnDTO(doctor1)
        ).getJson();

        //then
        assertEquals(expectedJson, response.getContentAsString());
    }

    @Test
    @DisplayName("It should return code 200 and a valid body")
    @WithMockUser
    void getAllDoctorsScenario1() throws Exception {
        //given
        var newDoctorReturnDto = new DoctorReturnDTO(doctor1);
        var newDoctorReturnDto2 = new DoctorReturnDTO(doctor2);
        List<DoctorReturnDTO> doctorReturnDtoList = List.of(newDoctorReturnDto, newDoctorReturnDto2);
        Page<DoctorReturnDTO> page = new PageImpl<>(doctorReturnDtoList);

        when(doctorService.getAllDoctors(any())).thenReturn(page);

        //when
        var response = mvc.perform(get("/doctors"))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertNotNull(response);
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

    @Test
    @DisplayName("It should return code 200 when data provided for the api is valid, and the data in the response should" +
            "match the provided for the route api")
    @WithMockUser
    void updateDoctorScenario1() throws Exception {
        //given
        var updateInfo = new DoctorUpdateDTO(1l, "novo nome", "61999999999", addressData());
        doctor1.updateData(updateInfo);
        var doctorReturn = new DoctorReturnDTO(doctor1);
        when(doctorService.updateDoctor(any())).thenReturn(
                doctorReturn
        );

        var doctorExpected = doctorReturnDTOJacksonTester.write(doctorReturn).getJson();

        //when
        var response = mvc.perform(
                        put("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(doctorUpdateDTOJacksonTester.write(
                                        new DoctorUpdateDTO(1l, "novo nome", "61999999999", addressData())
                                ).getJson())
                )
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(doctorExpected);
    }

    @Test
    @DisplayName("It should return an error 400 when the request has no body with valid data")
    @WithMockUser
    void updateDoctorScenario2() throws Exception {
        //given
        String emptyJson = "{}";

        //when
        var response = mvc.perform(
                        put("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(emptyJson)
                )
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return code 204 when the correct requested doctor_id is provided")
    @WithMockUser
    void deleteDoctorScenario1() throws Exception {
        ///given
        doctor1.setId(1l);

        //when
        var response = mvc.perform(
                        delete("/doctors/" + doctor1.getId())
                )
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
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
