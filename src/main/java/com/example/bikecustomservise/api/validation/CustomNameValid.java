package com.example.bikecustomservise.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomerNameValidator.class)
public @interface CustomNameValid {
    String message() default "Password do not constrain any rule";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
