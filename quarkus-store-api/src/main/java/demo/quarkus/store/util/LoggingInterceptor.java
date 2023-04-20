package demo.quarkus.store.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Loggable
@Interceptor
public class LoggingInterceptor
        implements Serializable
{

    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @AroundInvoke
    public Object intercept( InvocationContext ic )
            throws Exception
    {
        logger.debug( ">>> " + ic.getTarget().getClass().getName() + "-" + ic.getMethod().getName() );
        try
        {
            return ic.proceed();
        }
        finally
        {
            logger.debug( "<<< " + ic.getTarget().getClass().getName() + "-" + ic.getMethod().getName() );
        }
    }
}
