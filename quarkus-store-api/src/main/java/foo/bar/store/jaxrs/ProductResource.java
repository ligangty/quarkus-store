package foo.bar.store.jaxrs;

import foo.bar.store.model.Product;
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

@Path( "/api/products" )
@Loggable
@Tag( name = "Product" )
public class ProductResource
{

    @Inject
    EntityManager em;

    @POST
    @Consumes( APPLICATION_JSON )
    @Operation( description = "Creates new product" )
    @Transactional
    public Response create( Product entity )
    {
        em.persist( entity );
        return Response.created(
                               UriBuilder.fromResource( ProductResource.class ).path( String.valueOf( entity.getId() ) ).build() )
                       .build();
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    @Operation( description = "Deletes a product by id" )
    @Transactional
    public Response deleteById( @PathParam( "id" ) Long id )
    {
        Product entity = em.find( Product.class, id );
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
    @Operation( description = "Finds a product by id" )
    public Response findById( @PathParam( "id" ) Long id )
    {
        TypedQuery<Product> findByIdQuery = em.createQuery(
                "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category WHERE p.id = :entityId ORDER BY p.id",
                Product.class );
        findByIdQuery.setParameter( "entityId", id );
        Product entity;
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
    @Operation( description = "Lists all products" )
    public List<Product> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult )
    {
        TypedQuery<Product> findAllQuery =
                em.createQuery( "SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.category ORDER BY p.id",
                                Product.class );
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
    @Operation( description = "Updates a product" )
    @Transactional
    public Response update( @PathParam( "id" ) final Long id, final Product entity )
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

    @GET
    @Path( "/byCategory" )
    @Produces( APPLICATION_JSON )
    public Response findProductsByCategory( @QueryParam( "category" ) final String categoryName )
    {
        TypedQuery<Product> typedQuery = em.createNamedQuery( Product.FIND_BY_CATEGORY_NAME, Product.class );
        typedQuery.setParameter( "pname", categoryName );
        return Response.ok( typedQuery.getResultList() ).build();
    }
}
