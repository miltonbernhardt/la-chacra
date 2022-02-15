package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = "/api/v1/lotes";

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    void Get__OK() throws JsonProcessingException {
        String expectedLote = mapper.writeValueAsString(mockLoteDTO1());
        var response = restTemplate.getForEntity(baseUrl.concat("/221020210011"), SuccessfulResponse.class);
        var actualLote = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLote, actualLote);
    }

    @Test
    void Get__Lote_Not_Found() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> restTemplate.getForEntity(baseUrl.concat("/1"), ErrorResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("/1"), response.getPath());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    void Get_All__OK() throws JsonProcessingException {
        String expectedLotes = mapper.writeValueAsString(List.of(mockLoteDTO1(), mockLoteDTO2(), mockLoteDTO3()));
        var response = restTemplate.getForEntity(baseUrl.concat("/"), SuccessfulResponse.class);
        var actualLotes = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLotes, actualLotes);
    }

    @Test
    void Save__OK() {
        //todo
    }

    @Test
    void Save__Lote_Not_Found() {
        //todo
    }

    @Test
    void Update__OK() {
        //todo
    }

    @Test
    void Update__Lote_Not_Found() {
        //todo
    }

    @Test
    void Save__Queso_Not_Found_Conflict() {
        //todo
    }

    @Test
    void Delete__OK() {
        //todo
    }

    @Test
    void Delete__Lote_Not_Found() {
        //todo
    }

    LoteDTO mockLoteDTO1() {
        LoteDTO dto = new LoteDTO();
        dto.setId("221020210011");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        dto.setNumeroTina(1);
        dto.setLitrosLeche(4900D);
        dto.setCantHormas(124);
        dto.setStockLote(20);
        dto.setPeso(526.7);
        dto.setRendimiento(10.75);
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteColorante("colorante1, colorante2");
        dto.setLoteCalcio("calcio1, calcio2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(1L);
        return dto;
    }

    LoteDTO mockLoteDTO2() {
        LoteDTO dto = new LoteDTO();
        dto.setId("231020210022");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto.setNumeroTina(2);
        dto.setLitrosLeche(6500D);
        dto.setCantHormas(228);
        dto.setStockLote(25);
        dto.setPeso(842.5);
        dto.setRendimiento(12.96);
        dto.setIdQueso(2L);
        return dto;
    }

    LoteDTO mockLoteDTO3() {
        LoteDTO dto = new LoteDTO();
        dto.setId("241020210033");
        dto.setFechaElaboracion(LocalDate.of(2021, 10, 24));
        dto.setNumeroTina(3);
        dto.setLitrosLeche(6537D);
        dto.setCantHormas(242);
        dto.setStockLote(30);
        dto.setPeso(938.8);
        dto.setRendimiento(14.36);
        dto.setLoteCultivo("cultivo1, cultivo2");
        dto.setLoteCuajo("cuajo1, cuajo2");
        dto.setIdQueso(3L);
        return dto;
    }
}
