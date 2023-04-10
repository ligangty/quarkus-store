package demo.quarkus.store.ui.jaxrs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.ui.model.Customer;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Path( "/api/users" )
public class CustomerResource
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Inject
    SecurityIdentity identity;

    @Inject
    ObjectMapper mapper;

    @Inject
    @IdToken
    JsonWebToken token;

    @GET
    @Path( "loggedIn" )
    @Produces( APPLICATION_JSON )
    public Response loggedInCustomer()
    {
        Customer loggedInUser;

        Map<String, Object> attributes = identity.getAttributes();
        UserInfo userInfo = (UserInfo) attributes.get( "userinfo" );
        if ( userInfo != null && userInfo.getUserInfoString() != null )
        {
            try
            {
                Map userInfoMap = mapper.readValue( userInfo.getUserInfoString(), Map.class );
                logger.debug( "userInfo Map: {}", userInfoMap );
                loggedInUser = convertUser( userInfoMap );
            }
            catch ( JsonProcessingException e )
            {
                return Response.status( NOT_FOUND ).build();
            }
            Set<String> roles = identity.getRoles();
            logger.debug( "Roles {}", roles );

            return Response.ok( loggedInUser ).build();
        }

        return Response.status( NOT_FOUND ).build();
    }

    private Customer convertUser( Map<String, Object> userInfo )
    {
        Customer user = new Customer();
        String userName = (String) userInfo.get( "preferred_username" );
        if ( isNotBlank( userName ) )
        {
            user.setLogin( userName );
        }
        final String firstName = (String) userInfo.get( "given_name" );
        if ( isNotBlank( firstName ) )
        {
            user.setFirstName( firstName );
        }
        final String lastName = (String) userInfo.get( "family_name" );
        if ( isNotBlank( lastName ) )
        {
            user.setLastName( lastName );
        }
        final String email = (String) userInfo.get( "email" );
        if ( isNotBlank( email ) )
        {
            user.setEmail( email );
        }
        return user;
    }
}
