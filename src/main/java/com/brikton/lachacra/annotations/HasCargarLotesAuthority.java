package com.brikton.lachacra.annotations;

import com.brikton.lachacra.constants.Path;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('"+Path.CARGAR_LOTES+"')")
public @interface HasCargarLotesAuthority {
}