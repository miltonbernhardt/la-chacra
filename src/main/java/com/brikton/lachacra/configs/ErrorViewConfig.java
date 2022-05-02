package com.brikton.lachacra.configs;

import com.brikton.lachacra.constants.Path;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ErrorViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(Path.NOT_FOUND).setViewName("index");
        registry.addViewController(Path.FORBIDDEN).setViewName("index");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, Path.NOT_FOUND));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, Path.FORBIDDEN));
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, Path.LOGIN));
        };
    }
}
