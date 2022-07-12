package com.brikton.lachacra.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DateUtil {

    public DateUtil() {
    }

    public LocalDate now() {
        return LocalDate.now();
    }
}
