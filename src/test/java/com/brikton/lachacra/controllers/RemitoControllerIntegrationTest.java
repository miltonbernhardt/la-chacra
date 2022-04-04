package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.RemitoDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.utils.Rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RemitoControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private static Rest rest = null;

    @BeforeAll
    static void init() {
        rest = new Rest(Path.API_REMITOS);
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Generate_Remito__OK() {
        //TODO: generate items remito etc
        var expectedRemito = new RemitoDTO();
        expectedRemito.setFecha(LocalDate.of(2021, 11, 8));
        expectedRemito.setImporteTotal(0.0);
        expectedRemito.setItemsRemito(new ArrayList<>());

        var response = rest.get("/generate?id_cliente=1&fecha=2021-11-08");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<RemitoDTO>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedRemito, successfulResponse.getData());
    }

    @Test
    void Generate_Remito__Error__Missing_Cliente() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.get("/generate?id_cliente=1110&fecha=2021-11-08")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_REMITOS.concat("/generate"), response.getPath());
    }

    @Test
    void Generate_Remito__Error__Wrong_ID_Cliente() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/generate?id_cliente=0&fecha=2021-11-08")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(Path.API_REMITOS.concat("/generate"), response.getPath());
    }

    @Test
    void Generate_Remito__Error__Missing_Fecha() {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/generate?id_cliente=1&fecha=08-11-2021")
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Pdf__OK() {
        //TODO: test
    }

    @Test
    void Generate_And_Save__OK() {
        var expectedRemito = new RemitoDTO();
        expectedRemito.setFecha(LocalDate.of(2021, 11, 8));
        expectedRemito.setImporteTotal(0.0);
        expectedRemito.setItemsRemito(new ArrayList<>());

        var response = rest.post("?id_cliente=3&fecha=2021-11-10", null);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<RemitoDTO>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedRemito, successfulResponse.getData());
    }
}
