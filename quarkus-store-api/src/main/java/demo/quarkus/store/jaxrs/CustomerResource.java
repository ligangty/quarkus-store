package demo.quarkus.store.jaxrs;

import demo.quarkus.store.model.Customer;
import demo.quarkus.store.util.Loggable;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path( "/api/customers" )
@Loggable
@Tag( name = "Customer" )
@Deprecated
public class CustomerResource
{

    @Inject
    EntityManager em;

    @GET
    @Path( "/{login}" )
    @Produces( APPLICATION_JSON )
    @Operation( description = "Finds a customer by it identifier" )
    public Response findByLogin( @PathParam( "login" ) String login )
    {
        TypedQuery<Customer> findByIdQuery = em.createQuery(
                "SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.homeAddress.country WHERE c.login = :entityLogin ORDER BY c.id",
                Customer.class );
        findByIdQuery.setParameter( "entityLogin", login );
        Customer entity;
        try
        {
            entity = findByIdQuery.getSingleResult();
        }
        catch ( NoResultException nre )
        {
            entity = null;
        }
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
    @Transactional
    public Response update( @PathParam( "login" ) final String login, Customer entity )
    {
        try
        {
            em.merge( entity );
        }
        catch ( OptimisticLockException e )
        {
            return Response.status( Response.Status.CONFLICT ).entity( e.getEntity() ).build();
        }

        return Response.noContent().build();
    }
}
