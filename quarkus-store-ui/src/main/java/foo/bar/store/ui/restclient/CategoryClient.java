package foo.bar.store.ui.restclient;

import foo.bar.store.ui.model.Category;
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

@Path( "/api/categories" )
@RegisterRestClient( configKey = "store-service-api" )
public interface CategoryClient
{

    @POST
    @Consumes( APPLICATION_JSON )
    Response create( Category entity );

    @DELETE
    @Path( "/{id:[0-9][0-9]*}" )
    Response deleteById( @PathParam( "id" ) Long id );

    @GET
    @Path( "/{id:[0-9][0-9]*}" )
    @Produces( APPLICATION_JSON )
    Response findById( @PathParam( "id" ) Long id );

    @GET
    @Produces( APPLICATION_JSON )
    List<Category> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult );

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    Response update( @PathParam( "id" ) final Long id, Category entity );
}
