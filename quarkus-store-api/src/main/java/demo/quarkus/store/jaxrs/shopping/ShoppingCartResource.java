package demo.quarkus.store.jaxrs.shopping;

import demo.quarkus.store.service.ShoppingCartService;
import demo.quarkus.store.view.shopping.ShoppingCartItem;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path( "/api/cart" )
public class ShoppingCartResource
{
    @Inject
    ShoppingCartService service;

    @GET
    @Path( "/addItem/{itemId:[0-9][0-9]*}" )
    public Response addItemToCart( final @PathParam( "itemId" ) String itemId, final @Context UriInfo uriInfo )
    {
        boolean result = service.addItemToCart( Long.valueOf( itemId ) );
        if ( result )
        {
            return Response.ok().build();
        }
        else
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }

    @GET
    @Path( "/removeItem/{itemId:[0-9][0-9]*}" )
    public Response removeItemFromCart( final @PathParam( "itemId" ) String itemId )
    {
        boolean result = service.removeItemFromCart( Long.valueOf( itemId ) );
        if ( result )
        {
            return Response.accepted().build();
        }
        else
        {
            return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }

    @GET
    @Path( "/items" )
    public Response getCartItems()
    {
        List<ShoppingCartItem> items = service.getCartItems();
        if ( items != null && !items.isEmpty() )
        {
            return Response.ok( items ).build();
        }
        else
        {
            return Response.status( Response.Status.NOT_FOUND ).build();
        }
    }
}
