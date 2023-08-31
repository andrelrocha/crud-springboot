package rocha.andre.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rocha.andre.api.domain.appointment.AppointmentDetaillingData;
import rocha.andre.api.domain.appointment.AppointmentDto;
import rocha.andre.api.domain.appointment.CancelAppointmentDto;
import rocha.andre.api.domain.appointment.UseCase.ScheduleAppointmentsUseCase;
import rocha.andre.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentDto> dataAppointmentDtoJson;

    @Autowired
    private JacksonTester<AppointmentDetaillingData> dataAppointmentReturnJson;

    //cria um bean mocked para que nao seja efetuada a logica de negocio no use case, chamando repository
    @MockBean
    private ScheduleAppointmentsUseCase scheduleAppointmentsUseCase;

    @Test
    @DisplayName("It should return code 400 when data provided for the api is invalid")
    @WithMockUser
    void scheduleAppointmentFirstScenario() throws Exception {
        var response = mvc.perform(post("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("It should return code 403 when the route is called by a non logged user")
    void scheduleAppointmentSecondScenario() throws Exception {
        var response = mvc.perform(post("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("It should return code 200 when data provided for the api is valid")
    @WithMockUser
    void scheduleAppointmentThirdScenario() throws Exception {
        var date = LocalDateTime.now().plusHours(2);
        var specialty = Specialty.cardiology;

        //informa ao mockito para quando o usecase for chamado, ele retorne o objeto enviado na requisição, independente de parametros
        var dataDetailling = new AppointmentDetaillingData(null, 2l, 5l, date);
        when(scheduleAppointmentsUseCase.schedule(any())).thenReturn(
                dataDetailling);

        var response = mvc.perform(
                    post("/appointment")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dataAppointmentDtoJson.write(
                                    new AppointmentDto(2l, 5l, date, specialty)
                            ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = dataAppointmentReturnJson.write(
            dataDetailling
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("It should return code 400 when data provided for the api is invalid")
    @WithMockUser
    void cancelAppointmentFirstScenario() throws Exception {
        var response = mvc.perform(delete("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}