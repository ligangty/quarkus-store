package demo.quarkus.store.ui.restclient;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 * This filter is used to inject user access token to rest client for SSO purpose
 */
@Provider
@Priority( Priorities.AUTHENTICATION )
public class AuthClientRequestFilter
        implements ClientRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );


    @Inject
    JsonWebToken accessToken;



    @Override
    public void filter( ClientRequestContext requestContext )
    {
        if ( accessToken != null )
        {
            String token = accessToken.getRawToken();
            logger.debug( "Access Token: {}", token );
            requestContext.getHeaders().add( HttpHeaders.AUTHORIZATION, String.format( "Bearer %s", token ) );
        }
    }
}
