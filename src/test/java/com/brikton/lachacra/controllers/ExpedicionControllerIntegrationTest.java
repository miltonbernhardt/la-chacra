package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.ExpedicionDTO;
import com.brikton.lachacra.dtos.ExpedicionUpdateDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.utils.Rest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(DatabaseTestConfig.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExpedicionControllerIntegrationTest {

    private static Rest rest = null;
    @LocalServerPort
    private int port;

    @BeforeAll
    static void init() {
        rest = new Rest(Path.API_EXPEDICIONES);
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Get_All__OK() {
        var response = rest.get();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<ExpedicionDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(3, successfulResponse.getData().size());
    }

    @Test
    void Get_All_By_Lote__OK() {
        var response = rest.get("/lote?idLote=221020210011");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<ExpedicionDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(1, successfulResponse.getData().size());
    }

    @Test
    void Get_All_By_Lote__Lote_Not_Found() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.get("/lote?idLote=221020210000")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Get_All_By_Lote__Bad_Lote_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/lote?idLote=2210202100")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("idLote"));

        thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/lote?idLote=221020210000002")
        );
        response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("idLote"));

        thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/lote?idLote=2210aa210011")
        );
        response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("idLote"));

        thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/lote")
        );
        response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idLote"));
    }

    @Test
    void Save__OK() {
        var dtoToSave = new ExpedicionDTO();
        dtoToSave.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoToSave.setPeso(200.0);
        dtoToSave.setIdLote("221020210011");
        dtoToSave.setCantidad(1);
        dtoToSave.setIdCliente(1L);

        var expectedDTO = new ExpedicionDTO();
        expectedDTO.setId(4L);
        expectedDTO.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        expectedDTO.setImporte(97200.0);
        expectedDTO.setPeso(200.0);
        expectedDTO.setIdLote("221020210011");
        expectedDTO.setCantidad(1);
        expectedDTO.setIdCliente(1L);

        var response = rest.post(dtoToSave);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ExpedicionDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_EXPEDICION_CREATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Save__Cliente_Not_Found() throws JsonProcessingException {
        var dtoToSave = new ExpedicionDTO();
        dtoToSave.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoToSave.setPeso(200.0);
        dtoToSave.setIdLote("221020210011");
        dtoToSave.setCantidad(1);
        dtoToSave.setIdCliente(100L);

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.post(dtoToSave)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Save__Lote_Not_Found() throws JsonProcessingException {
        var dtoToSave = new ExpedicionDTO();
        dtoToSave.setFechaExpedicion(LocalDate.of(2021, 10, 11));
        dtoToSave.setPeso(200.0);
        dtoToSave.setIdLote("9921020210011");
        dtoToSave.setCantidad(1);
        dtoToSave.setIdCliente(1L);

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.post(dtoToSave)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Save__Invalid_Fields() throws JsonProcessingException {
        var dtoToSave1 = new ExpedicionDTO();

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(dtoToSave1)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(5, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("fechaExpedicion"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cantidad"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idCliente"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idLote"));

        var dtoToSave2 = new ExpedicionDTO();
        dtoToSave2.setFechaExpedicion(LocalDate.of(3000, 10, 10));
        dtoToSave2.setPeso(0.0);
        dtoToSave2.setIdLote(RandomStringUtils.randomAlphabetic(256));
        dtoToSave2.setCantidad(0);
        dtoToSave2.setIdCliente(0L);

        thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(dtoToSave2)
        );
        response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(5, response.getErrors().size());
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaExpedicion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("cantidad"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idCliente"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("idLote"));
    }

    @Test
    void Update_Same_Lote_Same_Quantity__OK() {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("221020210011");
        dtoToUpdate.setCantidad(100);
        dtoToUpdate.setIdCliente(2L);

        var expectedDTO = new ExpedicionDTO();
        expectedDTO.setId(1L);
        expectedDTO.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        expectedDTO.setPeso(600.3);
        expectedDTO.setIdLote("221020210011");
        expectedDTO.setCantidad(100);
        expectedDTO.setIdCliente(2L);
        expectedDTO.setImporte(270735.3);

        var response = rest.put(dtoToUpdate);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ExpedicionDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_EXPEDICION_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Update_Same_Lote_Different_Quantity__OK() {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("221020210011");
        dtoToUpdate.setCantidad(150);
        dtoToUpdate.setIdCliente(2L);

        var expectedDTO = new ExpedicionDTO();
        expectedDTO.setId(1L);
        expectedDTO.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        expectedDTO.setPeso(600.3);
        expectedDTO.setIdLote("221020210011");
        expectedDTO.setCantidad(150);
        expectedDTO.setIdCliente(2L);
        expectedDTO.setImporte(270735.3);

        var response = rest.put(dtoToUpdate);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ExpedicionDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_EXPEDICION_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Update_Different_Lote__OK() {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("241020210033");
        dtoToUpdate.setCantidad(150);
        dtoToUpdate.setIdCliente(2L);

        var expectedDTO = new ExpedicionDTO();
        expectedDTO.setId(1L);
        expectedDTO.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        expectedDTO.setPeso(600.3);
        expectedDTO.setIdLote("241020210033");
        expectedDTO.setCantidad(150);
        expectedDTO.setIdCliente(2L);
        expectedDTO.setImporte(279139.5);

        var response = rest.put(dtoToUpdate);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<ExpedicionDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_EXPEDICION_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Update__Expedicion_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(5L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("241020210033");
        dtoToUpdate.setCantidad(150);
        dtoToUpdate.setIdCliente(2L);

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_EXPEDICION_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Cliente_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("241020210033");
        dtoToUpdate.setCantidad(150);
        dtoToUpdate.setIdCliente(20L);

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CLIENTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Lote_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new ExpedicionUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setFechaExpedicion(LocalDate.of(2021, 10, 8));
        dtoToUpdate.setPeso(600.3);
        dtoToUpdate.setIdLote("9991020210033");
        dtoToUpdate.setCantidad(150);
        dtoToUpdate.setIdCliente(2L);

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Invalid_Fields() throws JsonProcessingException {
        var dtoToUpdate1 = new ExpedicionUpdateDTO();

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.put(dtoToUpdate1)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(6, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("fechaExpedicion"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cantidad"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idCliente"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idLote"));

        var dtoToUpdate2 = new ExpedicionUpdateDTO();
        dtoToUpdate2.setFechaExpedicion(LocalDate.of(3000, 10, 10));
        dtoToUpdate2.setId(0L);
        dtoToUpdate2.setPeso(0.0);
        dtoToUpdate2.setIdLote(RandomStringUtils.randomAlphabetic(256));
        dtoToUpdate2.setCantidad(0);
        dtoToUpdate2.setIdCliente(0L);

        thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.put(dtoToUpdate2)
        );
        response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(6, response.getErrors().size());
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaExpedicion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("cantidad"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idCliente"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("idLote"));
    }

    @Test
    void Delete__OK() {
        var response = rest.delete("/1");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessfulMessages.MSG_EXPEDICION_DELETED, response.getBody().getMessage());
    }

    @Test
    void Delete_Remito_Dependency__Cannot_Be_Deleted() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.delete("/3")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_EXPEDICION_CANNOT_BE_DELETED, response.getMessage());
        assertEquals(Path.API_EXPEDICIONES.concat("/3"), response.getPath());
    }

    @Test
    void Delete__Expedicion_Not_Found() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.delete("/111")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_EXPEDICION_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_EXPEDICIONES.concat("/111"), response.getPath());
    }

    @Test
    void Delete__Bad_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.delete("/0")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(Path.API_EXPEDICIONES.concat("/0"), response.getPath());
    }
}
