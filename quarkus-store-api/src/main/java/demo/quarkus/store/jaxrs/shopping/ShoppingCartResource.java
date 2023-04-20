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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
            return Response.status( Response.Status.BAD_REQUEST ).build();
        }
    }

    @GET
    @Path( "/removeItem/{itemId:[0-9][0-9]*}" )
    public Response removeItemFromCart( final @PathParam( "itemId" ) String itemId )
    {
        boolean result = service.removeItemFromCart( Long.valueOf( itemId ) );
        if ( result )
        {
            return Response.status( Response.Status.NO_CONTENT).build();
        }
        else
        {
            return Response.status( Response.Status.BAD_REQUEST ).build();
        }
    }

    @GET
    @Path( "/items" )
    @Produces( APPLICATION_JSON )
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
