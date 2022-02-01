package com.brikton.lachacra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableScheduling
public class LaChacraApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaChacraApplication.class, args);
    }

}
