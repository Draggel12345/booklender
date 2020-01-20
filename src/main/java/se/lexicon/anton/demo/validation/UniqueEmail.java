package se.lexicon.anton.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
public @interface UniqueEmail {

	String message() default "This Email name is already in the database";
	public Class<?>[] groups() default{};
	public Class<? extends Payload>[] payload() default{};
}
