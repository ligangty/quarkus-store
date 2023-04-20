package demo.quarkus.store.ui.restclient;

import demo.quarkus.store.ui.model.Product;
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

@Path( "/api/products" )
@RegisterRestClient( configKey = "store-service-api" )
public interface ProductClient
{
    @POST
    @Consumes( APPLICATION_JSON )
    Response create( Product entity );

    @DELETE
    @Path( "/{id}" )
    Response deleteById( @PathParam( "id" ) Long id );

    @GET
    @Path( "/{id}" )
    Response findById( @PathParam( "id" ) Long id );

    @GET
    List<Product> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult );

    @PUT
    @Path( "/{id}" )
    @Consumes( APPLICATION_JSON )
    Response update( @PathParam( "id" ) final Long id, final Product entity );

    @GET
    @Path( "/byCategory" )
    @Produces( APPLICATION_JSON )
    Response findProductsByCategory( @QueryParam( "category" ) final String categoryName );
}
