package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

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
    private final String path = "/api/v1/lotes/";

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
    }

    <T> ResponseEntity<T> deleteForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(null, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables));
    }

    static <T> T nonNull(@Nullable T result) {
        Assert.state(result != null, "No result");
        return result;
    }

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
        dto.setCodigoQueso("001");

        String expectedLote = mapper.writeValueAsString(dto);
        var response = restTemplate.getForEntity(baseUrl.concat("221020210011"), SuccessfulResponse.class);
        var actualLote = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLote, actualLote);
    }

    @Test
    void Get__Bad_ID() throws JsonProcessingException {
        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.getForEntity(baseUrl.concat("0"), SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("id"));
        assertEquals(path.concat("0"), response.getPath());
    }

    @Test
    void Get__Lote_Not_Found() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> restTemplate.getForEntity(baseUrl.concat("1"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("1"), response.getPath());
    }

    @Test
    void Get_All__OK() throws JsonProcessingException {
        LoteDTO dto1 = new LoteDTO();
        dto1.setId("221020210011");
        dto1.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        dto1.setNumeroTina(1);
        dto1.setLitrosLeche(4900D);
        dto1.setCantHormas(124);
        dto1.setStockLote(20);
        dto1.setPeso(526.7);
        dto1.setRendimiento(10.75);
        dto1.setLoteCultivo("cultivo1, cultivo2");
        dto1.setLoteColorante("colorante1, colorante2");
        dto1.setLoteCalcio("calcio1, calcio2");
        dto1.setLoteCuajo("cuajo1, cuajo2");
        dto1.setCodigoQueso("001");

        LoteDTO dto2 = new LoteDTO();
        dto2.setId("231020210022");
        dto2.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto2.setNumeroTina(2);
        dto2.setLitrosLeche(6500D);
        dto2.setCantHormas(228);
        dto2.setStockLote(25);
        dto2.setPeso(842.5);
        dto2.setRendimiento(12.96);
        dto2.setCodigoQueso("002");

        LoteDTO dto3 = new LoteDTO();
        dto3.setId("241020210033");
        dto3.setFechaElaboracion(LocalDate.of(2021, 10, 24));
        dto3.setNumeroTina(3);
        dto3.setLitrosLeche(6537D);
        dto3.setCantHormas(242);
        dto3.setStockLote(30);
        dto3.setPeso(938.8);
        dto3.setRendimiento(14.36);
        dto3.setLoteCultivo("cultivo1, cultivo2");
        dto3.setLoteCuajo("cuajo1, cuajo2");
        dto3.setCodigoQueso("003");

        String expectedLotes = mapper.writeValueAsString(List.of(dto1, dto2, dto3));
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        var actualLotes = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLotes, actualLotes);
    }

    @Test
    void Save__OK() throws JsonProcessingException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("001");

        LoteDTO expectedLote = new LoteDTO();
        expectedLote.setId("101020210011");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        expectedLote.setNumeroTina(1);
        expectedLote.setCantHormas(1);
        expectedLote.setStockLote(1);
        expectedLote.setLitrosLeche(20D);
        expectedLote.setPeso(10D);
        expectedLote.setRendimiento(50D);
        expectedLote.setLoteCultivo("cultivo1, cultivo2");
        expectedLote.setLoteColorante("colorante1, colorante2");
        expectedLote.setLoteCalcio("calcio1, calcio2");
        expectedLote.setLoteCuajo("cuajo1, cuajo2");
        expectedLote.setCodigoQueso("001");

        var expectedLoteString = mapper.writeValueAsString(expectedLote);

        var response = restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class);
        var actualLote = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoteString, actualLote);
    }

    @Test
    void Save__Queso_Not_Found_Conflict() throws JsonProcessingException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("011");

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__Queso_Deleted_Conflict() throws JsonProcessingException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("004");

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__InvalidFields__Fields_Not_Found() throws JsonProcessingException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setId("1");
        dtoToSave.setStockLote(1);
        dtoToSave.setRendimiento(1d);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(6, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("codigoQueso"));
    }

    @Test
    void Save__InvalidFields__Other_Validations() throws JsonProcessingException {
        LoteDTO dtoToSave = new LoteDTO();
        dtoToSave.setId("1");
        dtoToSave.setStockLote(1);
        dtoToSave.setRendimiento(1d);
        dtoToSave.setFechaElaboracion(LocalDate.of(3000, 10, 10));
        dtoToSave.setNumeroTina(-1);
        dtoToSave.setCantHormas(-1);
        dtoToSave.setLitrosLeche(-20D);
        dtoToSave.setPeso(-10D);
        dtoToSave.setLoteCultivo(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setLoteColorante(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setLoteCalcio(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setLoteCuajo(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setCodigoQueso("0010000");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(10, response.getErrors().size());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteColorante"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCultivo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCalcio"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCuajo"));
    }

    @Test
    void Update__OK() throws JsonProcessingException {
        LoteUpdateDTO dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("221020210011");
        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 21));
        dtoToUpdate.setNumeroTina(3);
        dtoToUpdate.setCantHormas(2);
        dtoToUpdate.setLitrosLeche(40D);
        dtoToUpdate.setPeso(20D);
        dtoToUpdate.setLoteCultivo("cultivo3, cultivo4");
        dtoToUpdate.setLoteColorante("colorante3, colorante4");
        dtoToUpdate.setLoteCalcio("calcio3, calcio4");
        dtoToUpdate.setLoteCuajo("cuajo3, cuajo4");
        dtoToUpdate.setCodigoQueso("002");

        LoteDTO expectedLote = new LoteDTO();
        expectedLote.setId("211020210023");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 21));
        expectedLote.setNumeroTina(3);
        expectedLote.setCantHormas(2);
        expectedLote.setStockLote(2);
        expectedLote.setLitrosLeche(40D);
        expectedLote.setPeso(20D);
        expectedLote.setRendimiento(50D);
        expectedLote.setLoteCultivo("cultivo3, cultivo4");
        expectedLote.setLoteColorante("colorante3, colorante4");
        expectedLote.setLoteCalcio("calcio3, calcio4");
        expectedLote.setLoteCuajo("cuajo3, cuajo4");
        expectedLote.setCodigoQueso("002");

        var expectedLoteString = mapper.writeValueAsString(expectedLote);

        var response = putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class);
        var actualLote = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoteString, actualLote);
    }

    @Test
    void Update__Lote_Not_Found() throws JsonProcessingException {
        LoteDTO dtoToUpdate = new LoteDTO();
        dtoToUpdate.setId("011020210011");
        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToUpdate.setNumeroTina(1);
        dtoToUpdate.setCantHormas(1);
        dtoToUpdate.setLitrosLeche(20D);
        dtoToUpdate.setPeso(10D);
        dtoToUpdate.setLoteCultivo("cultivo1, cultivo2");
        dtoToUpdate.setLoteColorante("colorante1, colorante2");
        dtoToUpdate.setLoteCalcio("calcio1, calcio2");
        dtoToUpdate.setLoteCuajo("cuajo1, cuajo2");
        dtoToUpdate.setCodigoQueso("001");

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Queso_Not_Found_Conflict() throws JsonProcessingException {
        LoteDTO dtoToUpdate = new LoteDTO();
        dtoToUpdate.setId("221020210011");
        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        dtoToUpdate.setNumeroTina(1);
        dtoToUpdate.setCantHormas(1);
        dtoToUpdate.setLitrosLeche(20D);
        dtoToUpdate.setPeso(10D);
        dtoToUpdate.setLoteCultivo("cultivo1, cultivo2");
        dtoToUpdate.setLoteColorante("colorante1, colorante2");
        dtoToUpdate.setLoteCalcio("calcio1, calcio2");
        dtoToUpdate.setLoteCuajo("cuajo1, cuajo2");
        dtoToUpdate.setCodigoQueso("011");

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        LoteDTO dtoToUpdate = new LoteDTO();
        dtoToUpdate.setStockLote(1);
        dtoToUpdate.setRendimiento(1d);
        dtoToUpdate.setCodigoQueso("");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(7, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("codigoQueso"));
    }

    @Test
    void Update__Invalid_Fields__Other_Validations() throws JsonProcessingException {
        LoteUpdateDTO dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("");
        dtoToUpdate.setStockLote(1);
        dtoToUpdate.setRendimiento(1d);
        dtoToUpdate.setFechaElaboracion(LocalDate.of(3000, 10, 10));
        dtoToUpdate.setNumeroTina(-1);
        dtoToUpdate.setCantHormas(-1);
        dtoToUpdate.setLitrosLeche(-20D);
        dtoToUpdate.setPeso(-10D);
        dtoToUpdate.setLoteCultivo(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setLoteColorante(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setLoteCalcio(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setLoteCuajo(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setCodigoQueso("0010000");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(11, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteColorante"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCultivo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCalcio"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCuajo"));
    }

    @Test
    void Delete__OK() throws JsonProcessingException {
        var expectedID = mapper.writeValueAsString("221020210011");

        var response = deleteForEntity(baseUrl.concat("221020210011"), SuccessfulResponse.class);
        var actualID = mapper.writeValueAsString(Objects.requireNonNull(response.getBody()).getData());
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedID, actualID);
    }

    @Test
    void Delete__Lote_Not_Found() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> deleteForEntity(baseUrl.concat("1"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("1"), response.getPath());
    }

    @Test
    void Delete__Bad_ID() throws JsonProcessingException {
        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> deleteForEntity(baseUrl.concat("0"), SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_0, response.getErrors().get("id"));
        assertEquals(path.concat("0"), response.getPath());
    }
    //todo delete bad id x2
}
