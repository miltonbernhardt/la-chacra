package com.brikton.lachacra.security.examples;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		if ("user".equals(username) && "pass".equals(password)) {
			return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
		} else {
			throw new BadCredentialsException("Invalid Username or Password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) { //authentication is provided by the manager
        //this class supports UsernamePasswordAuthenticationToken
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
