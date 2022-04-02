package com.brikton.lachacra.controllers;

import com.brikton.lachacra.constants.Path;
import com.brikton.lachacra.constants.SuccessfulMessages;
import com.brikton.lachacra.dtos.UserLoginDTO;
import com.brikton.lachacra.entities.User;
import com.brikton.lachacra.responses.SuccessfulResponse;
import com.brikton.lachacra.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(value = Path.API_LOGIN)
    public ResponseEntity<SuccessfulResponse<String>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var header = userService.createCookieHeader(loginDTO);
        log.info("API - Login: {}", loginDTO.getUsername());
        return ResponseEntity.ok().headers(header).body(SuccessfulResponse.set(SuccessfulMessages.MSG_USER_LOGGED));
    }

    @GetMapping(value = Path.API_PERMISSIONS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessfulResponse<List<String>>> getPermisos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var principal = authentication.getPrincipal();

        if (principal instanceof User) {
            var permisos = userService.getPermisos((User) principal);
            log.info("API - permisos: {}", permisos);
            return ResponseEntity.ok().body(SuccessfulResponse.set(permisos));
        }

        return ResponseEntity.ok().body(SuccessfulResponse.set(""));
    }
}
