package com.example.ContactList.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

@Component
public class EnumContactTypeValidator implements ConstraintValidator<EnumContactTypesPattern, Enum<?>> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumContactTypesPattern constraintAnnotation) {
        Class<? extends  Enum<?>> enumClass = constraintAnnotation.enumClass();
        enumValues = enumClass.getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) {
            return false;
        }
        return Arrays.asList(enumValues).contains(anEnum);
    }

}
