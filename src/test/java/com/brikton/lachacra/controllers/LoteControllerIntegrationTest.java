package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.dtos.LoteDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql("classpath:data_test.sql")
public class LoteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/lotes");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    public void Get_All_Lotes__OK() throws JsonProcessingException {
        String expectedLotes = mapper.writeValueAsString(List.of(mockLoteDTO1(), mockLoteDTO2(), mockLoteDTO3()));
        var response = restTemplate.getForEntity(baseUrl.concat("/"), SuccessfulResponse.class);
        var actualLotes = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK, response.getBody().getMessage());
        assertEquals(expectedLotes, actualLotes);
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
