package demo.quarkus.store.ui.jaxrs.shopping;

import demo.quarkus.store.ui.model.Item;
import demo.quarkus.store.ui.restclient.ItemClient;
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

@Path( "/api/items" )
public class ItemResource
{

    @Inject
    @RestClient
    ItemClient client;

    @POST
    @Consumes( APPLICATION_JSON )
    public Response create( Item entity )
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
    public List<Item> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult )
    {
        return client.listAll( startPosition, maxResult );
    }

    @PUT
    @Path( "/{id:[0-9][0-9]*}" )
    @Consumes( APPLICATION_JSON )
    public Response update( @PathParam( "id" ) final Long id, Item entity )
    {
        return client.update( id, entity );
    }

    @GET
    @Path( "/byProduct" )
    @Produces( APPLICATION_JSON )
    public Response findByProduct( @QueryParam( "productId" ) final Long productId ){
        return client.findByProduct( productId );
    }

    @GET
    @Path( "/byKeyword" )
    @Produces( APPLICATION_JSON )
    public Response findByProduct( @QueryParam( "keyword" ) final String keyword )
    {
        return client.searchByKeyword( keyword );
    }
}