package org.example.annotations;

import org.example.logger.MyLogger;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Qualifier {

    Class<?> impl();
}
