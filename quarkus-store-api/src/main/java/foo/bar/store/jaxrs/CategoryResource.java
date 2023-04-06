package foo.bar.store.jaxrs;

import foo.bar.store.model.Category;
import foo.bar.store.util.Loggable;
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

@Path( "/api/categories" )
@Loggable
@Tag( name = "Category" )
public class CategoryResource
{

    @Inject
    EntityManager em;

    @POST
    @Consumes( APPLICATION_JSON )
    @Operation( description = "Creates a category" )
    @Transactional
    public Response create( Category entity )
    {
        em.persist( entity );
        return Response.created(
                               UriBuilder.fromResource( CategoryResource.class ).path( String.valueOf( entity.getId() ) ).build() )
                       .build();
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    @Operation( description = "Deletes a category by id" )
    @Transactional
    public Response deleteById( @PathParam( "id" ) Long id )
    {
        Category entity = em.find( Category.class, id );
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
    @Operation( description = "Finds a category given an identifier" )
    public Response findById( @PathParam( "id" ) Long id )
    {
        TypedQuery<Category> findByIdQuery =
                em.createQuery( "SELECT DISTINCT c FROM Category c WHERE c.id = :entityId ORDER BY c.id",
                                Category.class );
        findByIdQuery.setParameter( "entityId", id );
        Category entity;
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
    @Operation( description = "Lists all the categories" )
    public List<Category> listAll( @QueryParam( "start" ) Integer startPosition,
                                   @QueryParam( "max" ) Integer maxResult )
    {
        TypedQuery<Category> findAllQuery =
                em.createQuery( "SELECT DISTINCT c FROM Category c ORDER BY c.id", Category.class );
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
    @Operation( description = "Updates a category" )
    @Transactional
    public Response update( @PathParam( "id" ) final Long id, Category entity )
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
