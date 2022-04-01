package com.brikton.lachacra.controllers;

import com.brikton.lachacra.configs.DatabaseTestConfig;
import com.brikton.lachacra.configs.NotSecurityConfigTest;
import com.brikton.lachacra.constants.ErrorMessages;
import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.constants.ValidationMessages;
import com.brikton.lachacra.dtos.PrecioDTO;
import com.brikton.lachacra.dtos.PrecioUpdateDTO;
import com.brikton.lachacra.responses.ErrorResponse;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
public class PrecioControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";
    private final String path = Path.API_PRECIOS.concat("/");

    private static RestTemplate restTemplate = null;
    private static ObjectMapper mapper = null;

    <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return nonNull(restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables));
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
        var precioDTO1 = new PrecioDTO();
        precioDTO1.setId(1L);
        precioDTO1.setValor(371.00);
        precioDTO1.setIdTipoCliente(1L);
        precioDTO1.setIdQueso(1L);

        var precioDTO2 = new PrecioDTO();
        precioDTO2.setId(2L);
        precioDTO2.setValor(486.00);
        precioDTO2.setIdTipoCliente(1L);
        precioDTO2.setIdQueso(2L);

        var precioDTO3 = new PrecioDTO();
        precioDTO3.setId(3L);
        precioDTO3.setValor(550.00);
        precioDTO3.setIdTipoCliente(1L);
        precioDTO3.setIdQueso(3L);

        var precioDTO4 = new PrecioDTO();
        precioDTO4.setId(4L);
        precioDTO4.setValor(580.00);
        precioDTO4.setIdTipoCliente(2L);
        precioDTO4.setIdQueso(1L);

        var precioDTO5 = new PrecioDTO();
        precioDTO5.setId(5L);
        precioDTO5.setValor(451.00);
        precioDTO5.setIdTipoCliente(2L);
        precioDTO5.setIdQueso(2L);

        var precioDTO6 = new PrecioDTO();
        precioDTO6.setId(6L);
        precioDTO6.setValor(465.00);
        precioDTO6.setIdTipoCliente(2L);
        precioDTO6.setIdQueso(3L);

        var precioDTO7 = new PrecioDTO();
        precioDTO7.setId(7L);
        precioDTO7.setValor(431.00);
        precioDTO7.setIdTipoCliente(3L);
        precioDTO7.setIdQueso(1L);

        var precioDTO8 = new PrecioDTO();
        precioDTO8.setId(8L);
        precioDTO8.setValor(850.00);
        precioDTO8.setIdTipoCliente(3L);
        precioDTO8.setIdQueso(2L);

        var expectedPrecios = List.of(precioDTO1, precioDTO2, precioDTO3, precioDTO4, precioDTO5, precioDTO6, precioDTO7, precioDTO8);
        var response = restTemplate.getForEntity(baseUrl, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<List<PrecioDTO>>>() {
        });
        assertEquals("", successfulResponse.getMessage());
        assertEquals(expectedPrecios, successfulResponse.getData());
    }

    @Test
    void Save__OK() {
        var dtoToSave = new PrecioDTO();
        dtoToSave.setValor(371.00);
        dtoToSave.setIdTipoCliente(3L);
        dtoToSave.setIdQueso(3L);

        var expectedPrecio = new PrecioDTO();
        expectedPrecio.setId(9L);
        expectedPrecio.setValor(371.00);
        expectedPrecio.setIdTipoCliente(3L);
        expectedPrecio.setIdQueso(3L);

        var response = restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<PrecioDTO>>() {
        });
        assertEquals(SuccessfulMessages.MSG_PRECIO_CREATED, successfulResponse.getMessage());
        assertEquals(expectedPrecio, successfulResponse.getData());
    }

    @Test
    void Save__Queso_Not_Found_Conflict() throws JsonProcessingException {
        PrecioDTO dtoToSave = new PrecioDTO();
        dtoToSave.setValor(371.00);
        dtoToSave.setIdTipoCliente(1L);
        dtoToSave.setIdQueso(5L);

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_QUESO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__Tipo_Cliente_Not_Found_Conflict() throws JsonProcessingException {
        PrecioDTO dtoToSave = new PrecioDTO();
        dtoToSave.setValor(371.00);
        dtoToSave.setIdTipoCliente(4L);
        dtoToSave.setIdQueso(1L);

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_TIPO_CLIENTE_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__Precio_Already_Exists_Conflict() throws JsonProcessingException {
        PrecioDTO dtoToSave = new PrecioDTO();
        dtoToSave.setValor(371.00);
        dtoToSave.setIdTipoCliente(1L);
        dtoToSave.setIdQueso(1L);

        HttpClientErrorException.Conflict thrown = assertThrows(
                HttpClientErrorException.Conflict.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_PRECIO_ALREADY_EXISTS, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Save__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        PrecioDTO dtoToSave = new PrecioDTO();
        dtoToSave.setId(1L);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("valor"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idQueso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Save__Invalid_Fields__Other_Validations() throws JsonProcessingException {
        PrecioDTO dtoToSave = new PrecioDTO();
        dtoToSave.setValor(0D);
        dtoToSave.setIdTipoCliente(0L);
        dtoToSave.setIdQueso(0L);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> restTemplate.postForEntity(baseUrl, dtoToSave, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(3, response.getErrors().size());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("valor"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idQueso"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Update__OK() {
        var dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setValor(371.00);
        dtoToUpdate.setIdTipoCliente(1L);
        dtoToUpdate.setIdQueso(1L);

        var expectedPrecio = new PrecioDTO();
        expectedPrecio.setId(1L);
        expectedPrecio.setValor(371.00);
        expectedPrecio.setIdTipoCliente(1L);
        expectedPrecio.setIdQueso(1L);

        var response = putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        var successfulResponse = mapper.convertValue(response.getBody(), new TypeReference<SuccessfulResponse<PrecioDTO>>() {
        });
        assertEquals(expectedPrecio, successfulResponse.getData());
        assertEquals(SuccessfulMessages.MSG_PRECIO_UPDATED, successfulResponse.getMessage());
    }

    @Test
    void Update__Combination_Fields_Not_Valid__Conflict() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(2L);
        dtoToUpdate.setValor(500.00);
        dtoToUpdate.setIdTipoCliente(1L);
        dtoToUpdate.setIdQueso(1L);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Precio_Not_Exists_Conflict() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(9L);
        dtoToUpdate.setValor(371.00);
        dtoToUpdate.setIdTipoCliente(1L);
        dtoToUpdate.setIdQueso(1L);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Queso_Not_Found_Conflict() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setValor(371.00);
        dtoToUpdate.setIdTipoCliente(1L);
        dtoToUpdate.setIdQueso(5L);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Tipo_Cliente_Not_Found_Conflict() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(1L);
        dtoToUpdate.setValor(371.00);
        dtoToUpdate.setIdTipoCliente(4L);
        dtoToUpdate.setIdQueso(1L);

        HttpClientErrorException.NotFound thrown = assertThrows(
                HttpClientErrorException.NotFound.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );
        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_PRECIO_NOT_FOUND, response.getMessage());
        assertEquals(path, response.getPath());
    }

    @Test
    void Update__Invalid_Fields__Fields_Not_Found() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(4, response.getErrors().size());
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("id"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("valor"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idQueso"));
        assertEquals(ValidationMessages.NOT_FOUND, response.getErrors().get("idTipoCliente"));
    }

    @Test
    void Update__Invalid_Fields__Other_Validations() throws JsonProcessingException {
        PrecioUpdateDTO dtoToUpdate = new PrecioUpdateDTO();
        dtoToUpdate.setId(0L);
        dtoToUpdate.setValor(0D);
        dtoToUpdate.setIdTipoCliente(0L);
        dtoToUpdate.setIdQueso(0L);

        HttpClientErrorException.BadRequest thrown = assertThrows(
                HttpClientErrorException.BadRequest.class, () -> putForEntity(baseUrl, dtoToUpdate, SuccessfulResponse.class)
        );

        var response = mapper.readValue(thrown.getResponseBodyAsString(), ErrorResponse.class);
        assertEquals(path, response.getPath());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals(ErrorMessages.MSG_INVALID_BODY, response.getMessage());
        assertEquals(4, response.getErrors().size());
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("id"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("valor"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idQueso"));
        assertEquals(ValidationMessages.CANNOT_BE_LESS_THAN_1, response.getErrors().get("idTipoCliente"));
    }

}
