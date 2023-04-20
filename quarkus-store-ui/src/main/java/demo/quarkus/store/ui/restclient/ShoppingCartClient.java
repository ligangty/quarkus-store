package demo.quarkus.store.ui.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path( "/api/cart" )
@RegisterRestClient( configKey = "store-service-api" )
public interface ShoppingCartClient
{
    @GET
    @Path( "/addItem/{itemId}" )
    Response addItemToCart( final @PathParam( "itemId" ) String itemId );

    @GET
    @Path( "/removeItem/{itemId}" )
    Response removeItemFromCart( final @PathParam( "itemId" ) String itemId );

    @GET
    @Path( "/items" )
    Response getCartItems();
}
