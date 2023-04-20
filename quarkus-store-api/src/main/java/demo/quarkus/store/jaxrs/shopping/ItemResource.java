package demo.quarkus.store.jaxrs.shopping;

import demo.quarkus.store.model.Item;
import demo.quarkus.store.service.ItemService;
import demo.quarkus.store.util.Loggable;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
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

@Path( "/api/items" )
@Loggable
@Tag( name = "Item" )
public class ItemResource
{

    @Inject
    ItemService service;

    @POST
    @Consumes( APPLICATION_JSON )
    public Response create( Item entity )
    {
        service.persist( entity );
        return Response.created(
                               UriBuilder.fromResource( ItemResource.class ).path( String.valueOf( entity.getId() ) ).build() )
                       .build();
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    public Response deleteById( @PathParam( "id" ) Long id )
    {
        Item entity = service.findById( id );
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
    public Response findById( @PathParam( "id" ) Long id )
    {
        Item entity = service.findById( id );
        if ( entity == null )
        {
            return Response.status( Status.NOT_FOUND ).build();
        }
        return Response.ok( entity ).build();
    }

    @GET
    @Produces( APPLICATION_JSON )
    public List<Item> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult )
    {
        return service.listAll( startPosition, maxResult );
    }

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    @Transactional
    public Response update( @PathParam( "id" ) final Long id, Item entity )
    {
        try
        {
            service.merge( entity );
        }
        catch ( OptimisticLockException e )
        {
            return Response.status( Status.CONFLICT ).entity( e.getEntity() ).build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path( "/byProduct" )
    @Produces( APPLICATION_JSON )
    public Response findByProduct( @QueryParam( "productId" ) final Long productId )
    {
        return Response.ok( service.findItemsByProduct( productId ) ).build();
    }

    @GET
    @Path( "/byKeyword" )
    @Produces( APPLICATION_JSON )
    public Response searchByKeyword( @QueryParam( "keyword" ) final String keyword )
    {
        return Response.ok( service.searchItems( keyword ) ).build();
    }

}
