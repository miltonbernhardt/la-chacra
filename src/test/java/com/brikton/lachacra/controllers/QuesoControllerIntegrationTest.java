package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.NotSecurityConfigTest;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.QuesoDTO;
import com.brikton.lachacra.dtos.QuesoUpdateDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseTestConfig.class, NotSecurityConfigTest.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:data_test.sql"}, executionPhase = BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class QuesoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = Path.API_QUESOS.concat("/");

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
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(path);
    }

    @Test
    void Get_All__OK() {
        QuesoDTO mockQueso1 = new QuesoDTO();
        mockQueso1.setId(2L);
        mockQueso1.setCodigo("001");
        mockQueso1.setTipoQueso("Cremoso");
        mockQueso1.setNomenclatura("C");
        mockQueso1.setStock(70);

        QuesoDTO mockQueso2 = new QuesoDTO();
        mockQueso2.setId(3L);
        mockQueso2.setCodigo("002");
        mockQueso2.setTipoQueso("Barra");
        mockQueso2.setNomenclatura("B");
        mockQueso2.setStock(20);

        QuesoDTO mockQueso3 = new QuesoDTO();
        mockQueso3.setId(1L);
        mockQueso3.setCodigo("003");
        mockQueso3.setTipoQueso("Sardo");
        mockQueso3.setNomenclatura("S");
        mockQueso3.setStock(53);

        var expectedQuesos = List.of(mockQueso1, mockQueso2, mockQueso3);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<QuesoDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedQuesos, successfulResponse.getData());
    }

    @Test
    void Save__OK() {
        var dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("006");
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(10);

        var expectedDTO = new QuesoDTO();
        expectedDTO.setId(5L);
        expectedDTO.setCodigo("006");
        expectedDTO.setTipoQueso("TIPOQUESO");
        expectedDTO.setNomenclatura("TIP");
        expectedDTO.setStock(10);
        expectedDTO.setColor("");

        var response = restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<QuesoDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_QUESO_CREATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Save__Codigo_Queso_Already_Exists() throws JsonProcessingException {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("003");
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(10);

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_CODIGO_QUESO_ALREADY_EXIST, response.getMessage());
    }

    @Test
    void Save__Over_Queso_Deleted() {
        var dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("004");
        dtoToSave.setTipoQueso("tipoQueso");
        dtoToSave.setNomenclatura("tip");
        dtoToSave.setStock(10);

        var expectedDTO = new QuesoDTO();
        expectedDTO.setId(5L);
        expectedDTO.setCodigo("004");
        expectedDTO.setTipoQueso("TIPOQUESO");
        expectedDTO.setNomenclatura("TIP");
        expectedDTO.setStock(10);
        expectedDTO.setColor("");

        var response = restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<QuesoDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_QUESO_CREATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Save__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setStock(10);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("tipoQueso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("codigo"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("nomenclatura"));
    }

    @Test
    void Save__Invalid_Fields__Other_Validations() throws JsonProcessingException {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("0010");
        dtoToSave.setTipoQueso(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setNomenclatura(RandomStringUtils.randomAlphabetic(300));
        dtoToSave.setStock(10);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("tipoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("nomenclatura"));
    }

    @Test
    void Save__Invalid_Fields__Codigo_Too_Short() throws JsonProcessingException {
        QuesoDTO dtoToSave = new QuesoDTO();
        dtoToSave.setCodigo("10");
        dtoToSave.setTipoQueso("11a");
        dtoToSave.setNomenclatura("11a");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigo"));
    }

    @Test
    void Update__OK() {
        var dtoToUpdate = new QuesoUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setCodigo("005");
        dtoToUpdate.setTipoQueso("tipoQueso2");
        dtoToUpdate.setNomenclatura("tip2");
        dtoToUpdate.setStock(10);

        var expectedDTO = new QuesoDTO();
        expectedDTO.setId(1L);
        expectedDTO.setCodigo("003");
        expectedDTO.setTipoQueso("tipoQueso2");
        expectedDTO.setNomenclatura("tip2");
        expectedDTO.setStock(53);
        expectedDTO.setColor("");

        var response = putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<QuesoDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_QUESO_UPDATED, successfulResponse.getMessage());
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Update__Queso_Not_Found() throws JsonProcessingException {
        QuesoUpdateDTO dtoToUpdate = new QuesoUpdateDTO();
        dtoToUpdate.setId(11L);
        dtoToUpdate.setCodigo("011");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(10);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update__Over_Queso_Deleted__Not_Found() throws JsonProcessingException {
        QuesoUpdateDTO dtoToUpdate = new QuesoUpdateDTO();
        dtoToUpdate.setId(4L);
        dtoToUpdate.setCodigo("004");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(20);

        QuesoDTO expectedDTO = new QuesoDTO();
        expectedDTO.setId(4L);
        expectedDTO.setCodigo("004");
        expectedDTO.setTipoQueso("TIPOQUESO");
        expectedDTO.setNomenclatura("TIP");
        expectedDTO.setStock(0);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
    }

    @Test
    void Update_Same_Codigo_As_Deleted_Queso__OK() throws JsonProcessingException {
        var dtoToUpdate = new QuesoUpdateDTO();
        dtoToUpdate.setId(3L);
        dtoToUpdate.setCodigo("004");
        dtoToUpdate.setTipoQueso("tipoQueso");
        dtoToUpdate.setNomenclatura("tip");
        dtoToUpdate.setStock(30);

        var expectedDTO = new QuesoDTO();
        expectedDTO.setId(3L);
        expectedDTO.setCodigo("002");
        expectedDTO.setTipoQueso("TIPOQUESO");
        expectedDTO.setNomenclatura("TIP");
        expectedDTO.setStock(20);
        expectedDTO.setColor("");

        var response = putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<QuesoDTO>>() {
        });
        assertEquals(expectedDTO, successfulResponse.getData());
    }

    @Test
    void Update__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        var dtoToUpdate = new QuesoUpdateDTO();
        dtoToUpdate.setStock(10);
        dtoToUpdate.setCodigo("   ");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(4, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("tipoQueso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("codigo"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("nomenclatura"));
    }

    @Test
    void Update__Invalid_Fields__Other_Validations() throws JsonProcessingException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(0L);
        dtoToUpdate.setCodigo("0010");
        dtoToUpdate.setTipoQueso(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setNomenclatura(RandomStringUtils.randomAlphabetic(300));
        dtoToUpdate.setStock(10);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(4, response.getErrors().size());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigo"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("tipoQueso"));
        assertEquals(ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS, response.getErrors().get("nomenclatura"));
    }

    @Test
    void Update__Invalid_Fields__Codigo_Too_Short() throws JsonProcessingException {
        QuesoDTO dtoToUpdate = new QuesoDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setCodigo("10");
        dtoToUpdate.setTipoQueso("11a");
        dtoToUpdate.setNomenclatura("11a");

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationMessages.MUST_HAVE_3_CHARACTERS, response.getErrors().get("codigo"));
    }

    @Test
    void Delete_Queso_WITH_Dependencies__OK() {
        var response = deleteForEntity(baseUrl.concat("2"), SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<String>>() {
        });
        assertEquals(SuccessfulMessages.MSG_QUESO_DELETED, successfulResponse.getMessage());
        assertNull(successfulResponse.getData());
    }

    @Test
    void Delete_Queso_WITHOUT_Dependencies__OK() {
        var response = deleteForEntity(baseUrl.concat("1"), SuccessfulResponse.class);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<String>>() {
        });
        assertEquals(SuccessfulMessages.MSG_QUESO_DELETED, successfulResponse.getMessage());
        assertNull(successfulResponse.getData());
    }

    @Test
    void Delete__Bad_ID() throws JsonProcessingException {
        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> deleteForEntity(baseUrl.concat("0"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_PARAMS, response.getMessage());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(path.concat("0"), response.getPath());
    }

    @Test
    void Delete__Queso_Already_Deleted() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> deleteForEntity(baseUrl.concat("4"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("4"), response.getPath());
    }

    @Test
    void Delete__Queso_Not_Found() throws JsonProcessingException {
        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> deleteForEntity(baseUrl.concat("11"), SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path.concat("11"), response.getPath());
    }

    @Test
    void Delete__OK___After_That__Queso_Doesnt_Show_In_Get_All() {
        var mockQueso1 = new QuesoDTO();
        mockQueso1.setId(2L);
        mockQueso1.setCodigo("001");
        mockQueso1.setTipoQueso("Cremoso");
        mockQueso1.setNomenclatura("C");
        mockQueso1.setStock(70);

        var mockQueso2 = new QuesoDTO();
        mockQueso2.setId(3L);
        mockQueso2.setCodigo("002");
        mockQueso2.setTipoQueso("Barra");
        mockQueso2.setNomenclatura("B");
        mockQueso2.setStock(20);

        var mockQueso3 = new QuesoDTO();
        mockQueso3.setId(1L);
        mockQueso3.setCodigo("003");
        mockQueso3.setTipoQueso("Sardo");
        mockQueso3.setNomenclatura("S");
        mockQueso3.setStock(53);

        var expectedQuesos = List.of(mockQueso1, mockQueso2, mockQueso3);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var actualQuesos = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<QuesoDTO>>>() {
        }).getData();
        assertEquals(expectedQuesos, actualQuesos);


        //queso dado de baja
        response = deleteForEntity(baseUrl.concat("2"), SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        //queso borrado
        response = deleteForEntity(baseUrl.concat("1"), SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        expectedQuesos = List.of(mockQueso2);
        response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        actualQuesos = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<QuesoDTO>>>() {
        }).getData();
        assertEquals(expectedQuesos, actualQuesos);
    }
}
