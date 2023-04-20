package demo.quarkus.store.ui.jaxrs;

import demo.quarkus.store.common.auth.UserManager;
import demo.quarkus.store.common.auth.UserRole;
import demo.quarkus.store.ui.model.Customer;
import demo.quarkus.store.ui.restclient.CustomerClient;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Path( "/api/users" )
public class CustomerResource
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Inject
    UserManager principal;

    @Inject
    @RestClient
    CustomerClient client;

    @GET
    @Path( "loggedIn" )
    @Produces( APPLICATION_JSON )
    public Response loggedInCustomer()
    {
        Customer loggedInUser = getCustomer();

        if ( StringUtils.isNotBlank( loggedInUser.getLogin() ) )
        {
            return Response.ok( loggedInUser ).build();
        }

        return Response.status( NOT_FOUND ).build();
    }

    private Customer getCustomer()
    {
        Customer user = new Customer();
        Map<String, String> userAttrs = principal.getUserAttributes();
        String userName = userAttrs.get( UserManager.ATTR_USER_NAME );
        if ( isNotBlank( userName ) )
        {
            Customer detailedUser = client.findByLogin( userName );
            if ( detailedUser != null )
            {
                user = detailedUser;
            }
        }
        final String firstName = userAttrs.get( UserManager.ATTR_USER_FIRST_NAME );
        if ( isNotBlank( firstName ) )
        {
            user.setFirstName( firstName );
        }
        final String lastName = userAttrs.get( UserManager.ATTR_USER_LAST_NAME );
        if ( isNotBlank( lastName ) )
        {
            user.setLastName( lastName );
        }
        final String email = userAttrs.get( UserManager.ATTR_EMAIL );
        if ( isNotBlank( email ) )
        {
            user.setEmail( email );
        }
        if ( !principal.getRoles().isEmpty() )
        {
            user.setRole( principal.getRoles().stream().findFirst().get());
        }

        return user;
    }
}
