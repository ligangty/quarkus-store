package demo.quarkus.store.jaxrs.shopping;

import demo.quarkus.store.model.Country;
import demo.quarkus.store.service.CountryService;
import demo.quarkus.store.util.Loggable;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
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

@Path( "/api/countries" )
@Tag( name = "Country" )
public class CountryResource
{

    @Inject
    CountryService service;

    @POST
    @Consumes( APPLICATION_JSON )
    @Operation( description = "Creates a country" )
    public Response create( Country entity )
    {
        service.persist( entity );
        return Response.created(
                               UriBuilder.fromResource( CountryResource.class ).path( String.valueOf( entity.getId() ) ).build() )
                       .build();
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    @Operation( description = "Deletes a country given an id" )
    public Response deleteById( @PathParam( "id" ) Long id )
    {

        Country entity = service.findById( id );
        if ( entity == null )
        {
            return Response.status( Status.NOT_FOUND ).build();
        }
        service.remove( entity );
        return Response.noContent().build();
    }

    @GET
    @Path( "/{id:[0-9][0-9]*}" )
    @Produces( APPLICATION_JSON )
    @Operation( description = "Retrieves a country by its id" )
    public Response findById( @PathParam( "id" ) Long id )
    {
        Country entity = service.findById( id );
        if ( entity == null )
        {
            return Response.status( Status.NOT_FOUND ).build();
        }
        return Response.ok( entity ).build();
    }

    @GET
    @Produces( "application/json" )
    @Operation( description = "Lists all the countries" )
    public List<Country> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult )
    {
        return service.listAll(startPosition, maxResult);
    }

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    @Operation( description = "Updates a country" )
    public Response update( @PathParam( "id" ) final Long id, Country entity )
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
