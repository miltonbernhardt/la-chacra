package com.brikton.lachacra.dtos;

import com.brikton.lachacra.constants.ValidationMessages;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {
    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String username;

    @NotBlank(message = ValidationMessages.NOT_FOUND)
    @Length(max = 255, message = ValidationMessages.MUST_NOT_EXCEED_255_CHARACTERS)
    private String password;
}
