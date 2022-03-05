package com.brikton.lachacra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @Autowired
//    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/v1/quesos").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/api/v1/lotes").hasAnyRole("ADMIN")
                .and().csrf().disable();


        //usando esta, se deberia señalar la pagina de logueo
//        http.formLogin();
//                .loginPage("/")
//                .failureUrl("/") // default is /login?error
//                .loginProcessingUrl("/"); // default is /login


//        http.authorizeRequests().antMatchers("/api/v1/**").authenticated();
//        http.authorizeRequests().anyRequest().authenticated(); //solo permite las request autorizadas
//        http.authorizeRequests().anyRequest().permitAll(); //permite todas las request


//        http.addFilterBefore(new SecurityFilter(), BasicAuthenticationFilter.class); //ejemplo de un filtro 1
//        http.addFilterBefore(new SecurityFilterBean(), BasicAuthenticationFilter.class); //ejemplo de un filtro 2
//        http.addFilterBefore(new SecurityFilterOncePerRequest(), BasicAuthenticationFilter.class); //ejemplo de un filtro 3
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
