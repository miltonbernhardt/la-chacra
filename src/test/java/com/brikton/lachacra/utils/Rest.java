package com.brikton.lachacra.utils;

import com.brikton.lachacra.responses.SuccessfulResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Rest {
    private final String ADMIN_TOKEN = "tcEB4prfYRTni+jE3bHfFp92uaDG3EBXBHnvKVqjmJ+Ym77bflIwZykR74ha5USK46Wz84f+af7VzcUKk5+bSifsOCgQayOykPZLT9DxumKJ9BnDzgbyH8iQXDUFU49OG+8CzEiw9ieBdZFQx2AVm0XGlF8Us73C8HNpdrPHf4s=";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String path;
    private String url = "";

    public Rest(String path) {
        this.path = path;
        this.restTemplate = new RestTemplate();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void setPort(int port) {
        url = "http://localhost".concat(":").concat(port + "").concat(path);
    }

    public ObjectMapper mapper() {
        return mapper;
    }

    /* ---- POST ---- */

    public ResponseEntity<SuccessfulResponse> postUnauthorized(String url, @Nullable Object body) throws RestClientException {
        return post(url, body, null);
    }

    public ResponseEntity<SuccessfulResponse> postUnauthorized(@Nullable Object body) throws RestClientException {
        try {
            return post("/", mapper.writeValueAsString(body), null);
        } catch (JsonProcessingException e) {
            return post("/", body, null);
        }
    }

    public ResponseEntity<SuccessfulResponse> post(@Nullable Object body) throws RestClientException {
        try {
            return post("/", mapper.writeValueAsString(body), ADMIN_TOKEN);
        } catch (JsonProcessingException e) {
            return post("/", body, ADMIN_TOKEN);
        }
    }

    public ResponseEntity<SuccessfulResponse> post(String url, @Nullable Object body) throws RestClientException {
        return post(url, body, ADMIN_TOKEN);
    }

    private ResponseEntity<SuccessfulResponse> post(String url, @Nullable Object body, String token) throws RestClientException {
        var headers = new HttpHeaders();
        headers.add("Cookie", token == null ? "" :"authorizationToken=".concat(token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        var entity = new HttpEntity<>(body, headers);
        return restTemplate.postForEntity(this.url.concat(url), entity, SuccessfulResponse.class);
    }

    /* ---- PUT ---- */

    public ResponseEntity<SuccessfulResponse> put(@Nullable Object body) throws RestClientException {
        return put(this.url.concat("/"), body, ADMIN_TOKEN, SuccessfulResponse.class);
    }

    private <T> ResponseEntity<T> put(String url, @Nullable Object body, String token, Class<T> responseType, Object... uriVariables) throws RestClientException {
        var headers = new HttpHeaders();
        headers.add("Cookie", "authorizationToken=".concat(token));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity;

        try {
            entity = new HttpEntity<>(mapper.writeValueAsString(body), headers);
        } catch (JsonProcessingException e) {
            entity = new HttpEntity<>(body, headers);
        }

        var requestCallback = restTemplate.httpEntityCallback(entity, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }


    /* ---- DELETE ---- */

    public ResponseEntity<SuccessfulResponse> delete(String url) throws RestClientException {
        return delete(url, ADMIN_TOKEN);
    }

    private ResponseEntity<SuccessfulResponse> delete(String url, String token) throws RestClientException {
        var headers = new HttpHeaders();
        headers.add("Cookie", "authorizationToken=".concat(token));
        return restTemplate.exchange(this.url.concat(url), HttpMethod.DELETE, new HttpEntity<String>(headers), SuccessfulResponse.class);
    }


    /* ---- GET ---- */

    public ResponseEntity<SuccessfulResponse> get() throws RestClientException {
        return get("/", ADMIN_TOKEN);
    }

    public ResponseEntity<SuccessfulResponse> get(String path) throws RestClientException {
        return get(path, ADMIN_TOKEN);
    }

    public ResponseEntity<SuccessfulResponse> getUnauthorized(String path) throws RestClientException {
        return get(path, null);
    }

    private ResponseEntity<SuccessfulResponse> get(String path, String token) throws RestClientException {
        var headers = new HttpHeaders();
        headers.add("Cookie", token != null ? "authorizationToken=".concat(token) : null);
        return restTemplate.exchange(url.concat(path), HttpMethod.GET, new HttpEntity<String>(headers), SuccessfulResponse.class);
    }
}
