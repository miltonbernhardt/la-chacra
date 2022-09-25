package com.brikton.lachacra.util.validation;

import javax.validation.Payload;

public @interface ValidateString {

    String[] acceptedValues();

    String message() default "invalid string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
