package com.ivc.nikstanov.employeeservice.utill.validation.constraint;

import com.ivc.nikstanov.employeeservice.utill.validation.NameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {NameValidator.class})
public @interface ValidatedName{
    String message() default "name format error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
