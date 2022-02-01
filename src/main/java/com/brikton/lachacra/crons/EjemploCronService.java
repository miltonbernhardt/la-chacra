package com.brikton.lachacra.crons;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EjemploCronService {

    @Scheduled(cron = "* * * * *") //ToDo: esto iria en properties
    public void pruebaCron() {
        log.info("Probando cron");
    }

}
