package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.LoteDTO;
import com.brikton.lachacra.dtos.LoteUpdateDTO;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.litrosElaborados.LitrosElaboradosDTO;
import com.brikton.lachacra.dtos.litrosElaborados.LitrosElaboradosDiaDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoDiaDTO;
import com.brikton.lachacra.dtos.rendimiento.RendimientoQuesoDTO;
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
public class LoteControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private static Rest rest = null;

    @BeforeAll
    static void init() {
        rest = new Rest(Path.API_LOTES);
    }

    @BeforeEach
    void setUp() {
        rest.setPort(port);
    }

    @Test
    void Get_All__OK() {
        var dto1 = new LoteDTO();
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

        var dto2 = new LoteDTO();
        dto2.setId("231020210022");
        dto2.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto2.setNumeroTina(2);
        dto2.setLitrosLeche(6500D);
        dto2.setCantHormas(228);
        dto2.setStockLote(25);
        dto2.setPeso(842.5);
        dto2.setRendimiento(12.96);
        dto2.setCodigoQueso("001");

        var dto3 = new LoteDTO();
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
        dto3.setCodigoQueso("002");

        var expectedLotes = List.of(dto1, dto2, dto3);
        var response = rest.get();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<LoteDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedLotes, successfulResponse.getData());
    }

    @Test
    void Get_All_By_Queso__OK() {
        var dto1 = new LoteDTO();
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

        var dto2 = new LoteDTO();
        dto2.setId("231020210022");
        dto2.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto2.setNumeroTina(2);
        dto2.setLitrosLeche(6500D);
        dto2.setCantHormas(228);
        dto2.setStockLote(25);
        dto2.setPeso(842.5);
        dto2.setRendimiento(12.96);
        dto2.setCodigoQueso("001");

        var expectedLotes = List.of(dto1, dto2);
        var response = rest.get("/queso?codigoQueso=001");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<LoteDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedLotes, successfulResponse.getData());
    }

    @Test
    void Get_All_By_Queso__Bad_Codigo__Missing_Codigo() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/queso")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("codigoQueso"));
        assertEquals(Path.API_LOTES.concat("/queso"), response.getPath());
    }

    @Test
    void Get_All_By_Queso__Queso_Not_Found() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.get("/queso?codigoQueso=101")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_LOTES.concat("/queso"), response.getPath());
    }

    @Test
    void Get_All_By_Queso__Bad_Codigo__Short_Codigo() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/queso?codigoQueso=01")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(Path.API_LOTES.concat("/queso"), response.getPath());
    }

    @Test
    void Get_All_By_Queso__Bad_Codigo__Long_Codigo() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/queso?codigoQueso=0101")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(Path.API_LOTES.concat("/queso"), response.getPath());
    }

    @Test
    void Get_By_Id__OK() {
        var expectedDTO = new LoteDTO();
        expectedDTO.setId("221020210011");
        expectedDTO.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        expectedDTO.setNumeroTina(1);
        expectedDTO.setLitrosLeche(4900D);
        expectedDTO.setCantHormas(124);
        expectedDTO.setStockLote(20);
        expectedDTO.setPeso(526.7);
        expectedDTO.setRendimiento(10.75);
        expectedDTO.setLoteCultivo("cultivo1, cultivo2");
        expectedDTO.setLoteColorante("colorante1, colorante2");
        expectedDTO.setLoteCalcio("calcio1, calcio2");
        expectedDTO.setLoteCuajo("cuajo1, cuajo2");
        expectedDTO.setCodigoQueso("001");

        var response = rest.get("/221020210011");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<LoteDTO>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());

    }

    @Test
    void Get_By_Id__Lote_Not_Found() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.get("/221023310011")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_LOTES.concat("/221023310011"), response.getPath());
    }

    @Test
    void Get_By_Id__Bad_ID__Short_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/22102021001")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/22102021001"), response.getPath());
    }

    @Test
    void Get_By_Id__Bad_ID__Long_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/221020210012222")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/221020210012222"), response.getPath());
    }

    @Test
    void Get_By_Id__Bad_ID__Use_Letters() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get("/aaaaaaaaaaaa")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/aaaaaaaaaaaa"), response.getPath());
    }

    @Test
    void Save__OK() {
        var dtoToSave = new LoteDTO();
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

        var expectedLote = new LoteDTO();
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
        var response = rest.post(dtoToSave);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<LoteDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_LOTE_CREATED, successfulResponse.getMessage());
        assertEquals(expectedLote, successfulResponse.getData());
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

        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.post(dtoToSave)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
    }

    @Test
    void Save__Queso_Deleted_Conflict() throws JsonProcessingException {
        var dtoToSave = new LoteDTO();
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

        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.post(dtoToSave)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
    }

    @Test
    void Save__Lote_Already_Exists() throws JsonProcessingException {
        var dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        dtoToSave.setNumeroTina(1);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("001");

        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.post(dtoToSave)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_ALREADY_EXIST, response.getMessage());
    }

    @Test
    void Save__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        var dtoToSave = new LoteDTO();
        dtoToSave.setId("1");
        dtoToSave.setStockLote(1);
        dtoToSave.setRendimiento(1d);

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(dtoToSave)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
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
    void Save__Invalid_Fields__Other_Validations_1() throws JsonProcessingException {
        var dtoToSave = new LoteDTO();
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

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(dtoToSave)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(10, response.getErrors().size());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteColorante"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCultivo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCalcio"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCuajo"));
    }

    @Test
    void Save__Invalid_Fields__Other_Validations_2() throws JsonProcessingException {
        var dtoToSave = new LoteDTO();
        dtoToSave.setFechaElaboracion(LocalDate.of(2021, 10, 10));
        dtoToSave.setNumeroTina(1000);
        dtoToSave.setCantHormas(1);
        dtoToSave.setLitrosLeche(20D);
        dtoToSave.setPeso(10D);
        dtoToSave.setLoteCultivo("cultivo1, cultivo2");
        dtoToSave.setLoteColorante("colorante1, colorante2");
        dtoToSave.setLoteCalcio("calcio1, calcio2");
        dtoToSave.setLoteCuajo("cuajo1, cuajo2");
        dtoToSave.setCodigoQueso("11");

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.post(dtoToSave)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(2, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_BE_LESS_THAN_1000, response.getErrors().get("numeroTina"));
    }

    @Test
    void Update__OK() {
        var dtoToUpdate = new LoteUpdateDTO();
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

        var expectedLote = new LoteDTO();
        expectedLote.setId("211020210023");
        expectedLote.setFechaElaboracion(LocalDate.of(2021, 10, 21));
        expectedLote.setNumeroTina(3);
        expectedLote.setCantHormas(2);
        expectedLote.setLitrosLeche(40D);
        expectedLote.setPeso(20D);
        expectedLote.setRendimiento(50D);
        expectedLote.setLoteCultivo("cultivo3, cultivo4");
        expectedLote.setLoteColorante("colorante3, colorante4");
        expectedLote.setLoteCalcio("calcio3, calcio4");
        expectedLote.setLoteCuajo("cuajo3, cuajo4");
        expectedLote.setCodigoQueso("002");

        var response = rest.put(dtoToUpdate);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<LoteDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_LOTE_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedLote, successfulResponse.getData());
    }

    @Test
    void Update__Lote_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new LoteDTO();
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

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Lote_Deleted() throws JsonProcessingException {
        var dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("251020210045");
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

        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Queso_Not_Found_Conflict() throws JsonProcessingException {
        var dtoToUpdate = new LoteDTO();
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

        var thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> rest.put(dtoToUpdate)
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new LoteDTO();
        dtoToUpdate.setStockLote(1);
        dtoToUpdate.setRendimiento(1d);

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.put(dtoToUpdate)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
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
    void Update__Invalid_Fields__Other_Validations_1() throws JsonProcessingException {
        var dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("11111111111");
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

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.put(dtoToUpdate)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(11, response.getErrors().size());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("cantHormas"));
        assertEquals(ValidationMessages.CANT_BE_LATER_THAN_TODAY, response.getErrors().get("fechaElaboracion"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("numeroTina"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("litrosLeche"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("peso"));
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteColorante"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCultivo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCalcio"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("loteCuajo"));
    }

    @Test
    void Update__Invalid_Fields__Other_Validations_2() throws JsonProcessingException {
        var dtoToUpdate = new LoteUpdateDTO();
        dtoToUpdate.setId("111111111111111");
        dtoToUpdate.setFechaElaboracion(LocalDate.of(2021, 10, 21));
        dtoToUpdate.setNumeroTina(1000);
        dtoToUpdate.setCantHormas(2);
        dtoToUpdate.setLitrosLeche(40D);
        dtoToUpdate.setPeso(20D);
        dtoToUpdate.setLoteCultivo("cultivo3, cultivo4");
        dtoToUpdate.setLoteColorante("colorante3, colorante4");
        dtoToUpdate.setLoteCalcio("calcio3, calcio4");
        dtoToUpdate.setLoteCuajo("cuajo3, cuajo4");
        dtoToUpdate.setCodigoQueso("02");

        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.put(dtoToUpdate)
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigoQueso"));
        assertEquals(ValidationMessages.MUST_BE_LESS_THAN_1000, response.getErrors().get("numeroTina"));
    }

    @Test
    void Delete_Lote_Without_Dependencies__OK() {
        var response = rest.delete("/231020210022");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessfulMessages.MSG_LOTE_DELETED, response.getBody().getMessage());
    }

    @Test
    void Delete_Lote_With_Dependencies__OK() {
        var response = rest.delete("/241020210033");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(SuccessfulMessages.MSG_LOTE_DELETED, response.getBody().getMessage());
    }

    @Test
    void Delete__Lote_Not_Found() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.delete("/1122333344455")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_LOTES.concat("/1122333344455"), response.getPath());
    }

    @Test
    void Delete__Lote_Already_Deleted() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> rest.delete("/251020210045")
        );
        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_LOTE_NOT_FOUND, response.getMessage());
        assertEquals(Path.API_LOTES.concat("/251020210045"), response.getPath());
    }

    @Test
    void Delete__Bad_ID_Use_Letters() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.delete("/aaaaaaaaaaaaa")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/aaaaaaaaaaaaa"), response.getPath());
    }

    @Test
    void Delete__Bad_ID_Short_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.delete("/11111111111")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/11111111111"), response.getPath());
    }

    @Test
    void Delete__Bad_ID_Long_ID() throws JsonProcessingException {
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.delete("/111111111111111")
        );

        var response = rest.mapper().readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.INVALID_FORMAT, response.getErrors().get("id"));
        assertEquals(Path.API_LOTES.concat("/111111111111111"), response.getPath());
    }

    @Test
    void Delete__OK___After_That__Lote_Doesnt_Show_In_Get_All() {
        var dto1 = new LoteDTO();
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

        var dto2 = new LoteDTO();
        dto2.setId("231020210022");
        dto2.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto2.setNumeroTina(2);
        dto2.setLitrosLeche(6500D);
        dto2.setCantHormas(228);
        dto2.setStockLote(25);
        dto2.setPeso(842.5);
        dto2.setRendimiento(12.96);
        dto2.setCodigoQueso("001");

        var dto3 = new LoteDTO();
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
        dto3.setCodigoQueso("002");


        var expectedLotes = List.of(dto1, dto2, dto3);
        var response = rest.get();
        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<LoteDTO>>>() {
        });

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLotes, successfulResponse.getData());

        //lote dado de baja
        response = rest.delete("/241020210033");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //lote borrado
        response = rest.delete("/231020210022");
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        expectedLotes = List.of(dto1);
        response = rest.get();
        successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<>() {
        });

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLotes, successfulResponse.getData());
    }

    @Test
    void Get_Between_Dates__OK() {
        var dto1 = new LoteDTO();
        dto1.setId("221020210011");
        dto1.setFechaElaboracion(LocalDate.of(2021, 10, 22));
        dto1.setNumeroTina(1);
        dto1.setLitrosLeche(4900D);
        dto1.setCantHormas(124);
        dto1.setPeso(526.7);
        dto1.setCodigoQueso("001");
        dto1.setLoteColorante("colorante1, colorante2");
        dto1.setLoteCultivo("cultivo1, cultivo2");
        dto1.setLoteCalcio("calcio1, calcio2");
        dto1.setLoteCuajo("cuajo1, cuajo2");
        dto1.setRendimiento(10.75);
        dto1.setStockLote(20);

        var dto2 = new LoteDTO();
        dto2.setId("231020210022");
        dto2.setFechaElaboracion(LocalDate.of(2021, 10, 23));
        dto2.setNumeroTina(2);
        dto2.setLitrosLeche(6500D);
        dto2.setCantHormas(228);
        dto2.setPeso(842.5);
        dto2.setCodigoQueso("001");
        dto2.setRendimiento(12.96);
        dto2.setStockLote(25);

        var expectedLotes = List.of(dto1, dto2);

        var query = "/produccion?fecha_desde=2021-10-22&fecha_hasta=2021-10-23";
        var response = rest.get(query);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<LoteDTO>>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedLotes, successfulResponse.getData());
    }

    @Test
    void Get_Between_Dates__Bad_Request__Missing_Param() {
        var query = "/produccion?fecha_desde=2021-10-22";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Between_Dates__Bad_Request__Bad_Date_Format() {
        var query = "/produccion?fecha_desde=22-11-2021&fecha_hasta=23-10-2021";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Rendimiento_Por_Dia__OK() {
        var dto1 = new RendimientoDiaDTO();
        dto1.setRendimiento(10.75);
        dto1.setFecha(LocalDate.of(2021, 10, 22));

        var dto2 = new RendimientoDiaDTO();
        dto2.setRendimiento(12.96);
        dto2.setFecha(LocalDate.of(2021, 10, 23));

        var expectedRendimientos = List.of(dto1, dto2);

        var query = "/rendimiento/dia?fecha_desde=2021-10-22&fecha_hasta=2021-10-23";
        var response = rest.get(query);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<RendimientoDiaDTO>>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedRendimientos, successfulResponse.getData());
    }

    @Test
    void Get_Rendimiento_Por_Dia__Bad_Request__Missing_Param() {
        var query = "/rendimiento/dia?fecha_desde=2021-10-22";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Rendimiento_Por_Dia__Bad_Request__Bad_Date_Format() {
        var query = "/rendimiento/dia?fecha_desde=22-11-2021&fecha_hasta=23-10-2021";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Rendimiento_Por_Queso__OK() {
        var queso1 = new QuesoDTO();
        queso1.setCodigo("001");
        queso1.setTipoQueso("CREMOSO");
        queso1.setNomenclatura("C");
        queso1.setId(2L);
        queso1.setStock(70);

        var dto1 = new RendimientoQuesoDTO();
        dto1.setRendimiento(11.855);
        dto1.setQueso(queso1);

        var queso2 = new QuesoDTO();
        queso2.setCodigo("002");
        queso2.setTipoQueso("BARRA");
        queso2.setNomenclatura("B");
        queso2.setId(3L);
        queso2.setStock(20);

        var dto2 = new RendimientoQuesoDTO();
        dto2.setRendimiento(14.36);
        dto2.setQueso(queso2);

        var expectedRendimientos = List.of(dto1, dto2);

        var query = "/rendimiento/queso?fecha_desde=2021-10-22&fecha_hasta=2021-10-25";
        var response = rest.get(query);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<RendimientoQuesoDTO>>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedRendimientos, successfulResponse.getData());
    }

    @Test
    void Get_Rendimiento_Por_Queso__Bad_Request__Missing_Param() {
        var query = "/rendimiento/queso?fecha_desde=2021-10-22";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Rendimiento_Por_Queso__Bad_Request__Bad_Date_Format() {
        var query = "/rendimiento/queso?fecha_desde=22-11-2021&fecha_hasta=23-10-2021";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Litros_Elaborados__OK() {
        var dto1 = new LitrosElaboradosDiaDTO();
        dto1.setFecha(LocalDate.of(2021, 10, 22));
        dto1.setTotal(4900.0);
        dto1.setLitrosElaborados(List.of(new LitrosElaboradosDTO(4900.0, "001")));

        var dto2 = new LitrosElaboradosDiaDTO();
        dto2.setFecha(LocalDate.of(2021, 10, 23));
        dto2.setTotal(6500.0);
        dto2.setLitrosElaborados(List.of(new LitrosElaboradosDTO(6500.0, "001")));

        var dto3 = new LitrosElaboradosDiaDTO();
        dto3.setFecha(LocalDate.of(2021, 10, 24));
        dto3.setTotal(6537.0);
        dto3.setLitrosElaborados(List.of(new LitrosElaboradosDTO(6537.0, "002")));


        var expectedLitros = List.of(dto1, dto2, dto3);

        var query = "/litros_elaborados?fecha_desde=2021-10-22&fecha_hasta=2021-10-25";
        var response = rest.get(query);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = rest.mapper().convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<LitrosElaboradosDiaDTO>>>() {
        });

        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedLitros, successfulResponse.getData());
    }

    @Test
    void Get_Litros_Elaborados__Bad_Request__Missing_Param() {
        var query = "/litros_elaborados?fecha_desde=2021-10-22";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }

    @Test
    void Get_Litros_Elaborados__Bad_Request__Bad_Date_Format() {
        var query = "/litros_elaborados?fecha_desde=22-11-2021&fecha_hasta=23-10-2021";
        var thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> rest.get(query)
        );
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
    }
}
