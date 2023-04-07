package demo.quarkus.store.view;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Logger;

@Interceptor
@CatchException
public class ExceptionInterceptor
        implements Serializable
{

    @Inject
    Logger log;

    @AroundInvoke
    public Object catchException( InvocationContext ic )
            throws Exception
    {
        try
        {
            return ic.proceed();
        }
        catch ( Exception e )
        {
            addErrorMessage( e.getMessage() );
            log.severe( "/!\\ " + ic.getTarget().getClass().getName() + " - " + ic.getMethod().getName() + " - "
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
