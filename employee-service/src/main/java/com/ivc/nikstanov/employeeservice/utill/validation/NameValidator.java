package com.ivc.nikstanov.employeeservice.utill.validation;

import com.ivc.nikstanov.employeeservice.utill.validation.constraint.ValidatedName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<ValidatedName, String> {

    private static final Pattern PATTERN = Pattern.compile("^[A-Z][a-z]{1,256}$");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s != null){
            Matcher matcher = PATTERN.matcher(s);
            return matcher.find();
        }
        return true;
    }
}
