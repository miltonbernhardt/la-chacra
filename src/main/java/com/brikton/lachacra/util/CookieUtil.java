package com.brikton.lachacra.util;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    private final String cookieName = "authorizationToken";

    public HttpCookie createAccessTokenCookie(String token, Long duration) {
        String encryptedToken = SecurityUtil.encrypt(token);
        return ResponseCookie.from(cookieName, encryptedToken)
                .maxAge(duration)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public HttpHeaders deleteCookies() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.SET_COOKIE, deleteAccessTokenCookie().toString());
        return responseHeaders;
    }

    private HttpCookie deleteAccessTokenCookie() {
        return ResponseCookie.from(cookieName, "").maxAge(0).httpOnly(true).path("/").build();
    }
}
