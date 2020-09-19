package com.rev.modelo.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.constraints.Pattern;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoConfirmacaoValidator.class })
public @interface AtributoConfirmacao {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Senhas não conferem";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String atributo();

	String atributoConfirmacao();

}
