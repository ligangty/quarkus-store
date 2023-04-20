package demo.quarkus.store.common.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.HttpMethod.GET;

@ApplicationScoped
@Provider
public class SecurityInterceptor
        implements ContainerRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Context
    UriInfo info;

    @Inject
    UserManager principal;

    @Override
    public void filter( ContainerRequestContext requestContext )
    {

        final String path = info.getPath();

        final String method = requestContext.getMethod();

        if ( !authenticated( method ) )
        {
            requestContext.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
        }
    }

    private boolean authenticated( final String method )
    {
        if ( GET.equals( method ) )
        {
            logger.debug( "No access limitation for GET method." );
            return true;
        }
        String user = principal.getUserAttributes().get( UserManager.ATTR_USER_NAME );
        if ( StringUtils.isNotBlank( user ) )
        {
            logger.info( "Logged in user: {}", user );
            return true;
        }
        logger.info( "User not logged in, can not access non-GET methods" );
        return false;
    }

}
