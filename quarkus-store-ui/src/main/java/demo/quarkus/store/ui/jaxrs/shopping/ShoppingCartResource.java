package demo.quarkus.store.ui.jaxrs.shopping;

import demo.quarkus.store.ui.restclient.ShoppingCartClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path( "/api/cart" )
public class ShoppingCartResource
{
    @Inject
    @RestClient
    ShoppingCartClient client;

    @GET
    @Path( "/addItem/{itemId:[0-9][0-9]*}" )
    public Response addItemToCart( final @PathParam( "itemId" ) String itemId )
    {
        return client.addItemToCart( itemId );
    }

    @GET
    @Path( "/removeItem/{itemId:[0-9][0-9]*}" )
    public Response removeItemFromCart( final @PathParam( "itemId" ) String itemId )
    {
        return client.removeItemFromCart( itemId );
    }

    @GET
    @Path( "/items" )
    public Response getCartItems()
    {
        return client.getCartItems();
    }
}
