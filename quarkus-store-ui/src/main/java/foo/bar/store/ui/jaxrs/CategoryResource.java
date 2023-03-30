package foo.bar.store.ui.jaxrs;

import foo.bar.store.ui.model.Category;
import foo.bar.store.ui.restclient.CategoryClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
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

@Path( "/api/categories" )
public class CategoryResource
{
    @Inject
    @RestClient
    CategoryClient client;

    @POST
    @Consumes( APPLICATION_JSON )
    public Response create( Category entity )
    {
        return client.create( entity );
    }

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    public Response deleteById( @PathParam( "id" ) Long id )
    {
        return client.deleteById( id );
    }

    @GET
    @Path( "/{id:[0-9][0-9]*}" )
    @Produces( APPLICATION_JSON )
    public Response findById( @PathParam( "id" ) Long id )
    {
        return client.findById( id );
    }

    @GET
    @Produces( APPLICATION_JSON )
    public List<Category> listAll( @QueryParam( "start" ) Integer startPosition,
                                   @QueryParam( "max" ) Integer maxResult )
    {
        return client.listAll( startPosition, maxResult );
    }

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    public Response update( @PathParam( "id" ) final Long id, Category entity )
    {
        return client.update( id, entity );
    }
}
