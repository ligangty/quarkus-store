package demo.quarkus.store.util;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@InterceptorBinding
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD, ElementType.TYPE } )
@Documented
public @interface Loggable
{
}
