package demo.quarkus.store.ui.restclient;

import demo.quarkus.store.ui.model.Customer;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path( "/api/customers" )
@RegisterRestClient( configKey = "store-service-api" )
public interface CustomerClient
{
    @POST
    @Consumes( APPLICATION_JSON )
    Response create( Customer entity );

    @GET
    @Path( "/{login}" )
    @Produces( APPLICATION_JSON )
    Customer findByLogin( @PathParam( "login" ) String login );

    @PUT
    @Path( "/{login}" )
    @Consumes( APPLICATION_JSON )
    Response update( @PathParam( "login" ) final String login, Customer entity );
}
