package demo.quarkus.store.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@CatchException
public class ExceptionInterceptor
        implements Serializable
{

    private final Logger log = LoggerFactory.getLogger( this.getClass() );

    @AroundInvoke
    public Object catchException( InvocationContext ic )
    {
        try
        {
            return ic.proceed();
        }
        catch ( Exception e )
        {
            addErrorMessage( e.getMessage() );
            log.error( "/!\\ " + ic.getTarget().getClass().getName() + " - " + ic.getMethod().getName() + " - "
                                + e.getMessage() );
            e.printStackTrace();
        }
        return null;
    }

    // TODO to refactor with Controller methods
    protected void addErrorMessage( String message )
    {
    }
}
