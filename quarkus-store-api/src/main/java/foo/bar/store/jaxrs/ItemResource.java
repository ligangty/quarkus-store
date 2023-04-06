package foo.bar.store.jaxrs;

import foo.bar.store.model.Item;
import foo.bar.store.service.ItemService;
import foo.bar.store.util.Loggable;
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

@Path( "/api/items" )
@Loggable
@Tag( name = "Item" )
public class ItemResource
{

    @Inject
    EntityManager em;

    @Inject
    ItemService service;

    @POST
    @Consumes( APPLICATION_JSON )
    @Transactional
    public Response create( Item entity )
    {
        em.persist( entity );
        return Response.created(
                               UriBuilder.fromResource( ItemResource.class ).path( String.valueOf( entity.getId() ) ).build() )
                       .build();
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    @Transactional
    public Response deleteById( @PathParam( "id" ) Long id )
    {
        Item entity = em.find( Item.class, id );
        if ( entity == null )
        {
            return Response.status( Status.NOT_FOUND ).build();
        }
        em.remove( entity );
        return Response.noContent().build();
    }

    @GET
    @Path( "/{id:[0-9][0-9]*}" )
    @Produces( APPLICATION_JSON )
    public Response findById( @PathParam( "id" ) Long id )
    {
        TypedQuery<Item> findByIdQuery = em.createQuery(
                "SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.product WHERE i.id = :entityId ORDER BY i.id",
                Item.class );
        findByIdQuery.setParameter( "entityId", id );
        Item entity;
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

    @GET
    @Produces( APPLICATION_JSON )
    public List<Item> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult )
    {
        TypedQuery<Item> findAllQuery =
                em.createQuery( "SELECT DISTINCT i FROM Item i LEFT JOIN FETCH i.product ORDER BY i.id", Item.class );
        if ( startPosition != null )
        {
            findAllQuery.setFirstResult( startPosition );
        }
        if ( maxResult != null )
        {
            findAllQuery.setMaxResults( maxResult );
        }
        return findAllQuery.getResultList();
    }

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    @Transactional
    public Response update( @PathParam( "id" ) final Long id, Item entity )
    {
        try
        {
            em.merge( entity );
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
