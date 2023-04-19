package demo.quarkus.store.jaxrs;

import demo.quarkus.store.model.Customer;
import demo.quarkus.store.service.CustomerService;
import demo.quarkus.store.util.Loggable;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path( "/api/customers" )
@Loggable
@Tag( name = "Customer" )
public class CustomerResource
{

    @Inject
    CustomerService service;

    @GET
    @Path( "/{login}" )
    @Produces( APPLICATION_JSON )
    @Operation( description = "Finds a customer by it identifier" )
    public Response findByLogin( @PathParam( "login" ) String login )
    {
        Customer entity = service.findCustomer( login );
        if ( entity == null )
        {
            return Response.status( Status.NOT_FOUND ).build();
        }
        return Response.ok( entity ).build();
    }

    @PUT
    @Path( "/{login}" )
    @Consumes( APPLICATION_JSON )
    @Operation( description = "Updates a customer" )
    public Response update( @PathParam( "login" ) final String login, Customer entity )
    {
        try
        {
            service.merge( entity );
        }
        catch ( OptimisticLockException e )
        {
            return Response.status( Response.Status.CONFLICT ).entity( e.getEntity() ).build();
        }

        return Response.noContent().build();
    }
}
