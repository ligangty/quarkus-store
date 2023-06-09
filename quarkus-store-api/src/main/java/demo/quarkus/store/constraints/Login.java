package demo.quarkus.store.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint( validatedBy = {} )
@NotNull
@Size( min = 1, max = 10 )
@ReportAsSingleViolation
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR } )
@Documented
public @interface Login
{

    String message() default "Invalid login";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Retention( RetentionPolicy.RUNTIME )
    @Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE,
            ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR } )
    public @interface List
    {
        Login[] value();
    }
}
