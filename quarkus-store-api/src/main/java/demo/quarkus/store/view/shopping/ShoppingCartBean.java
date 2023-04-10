package demo.quarkus.store.view.shopping;

import demo.quarkus.store.model.CreditCard;
import demo.quarkus.store.model.CreditCardType;
import demo.quarkus.store.model.Customer;
import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.PurchaseOrder;
import demo.quarkus.store.service.CatalogService;
import demo.quarkus.store.service.PurchaseOrderService;
import demo.quarkus.store.util.Loggable;
import demo.quarkus.store.view.AbstractBean;
import demo.quarkus.store.view.CatchException;
import demo.quarkus.store.view.LoggedIn;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ConversationScoped
@Loggable
@CatchException
public class ShoppingCartBean
        extends AbstractBean
        implements Serializable
{

    @Inject
    @LoggedIn
    private Instance<Customer> loggedInCustomer;

    @Inject
    CatalogService catalogBean;

    @Inject
    PurchaseOrderService orderBean;

    @Inject
    Conversation conversation;

    private List<ShoppingCartItem> cartItems;

    private CreditCard creditCard = new CreditCard();

    private PurchaseOrder order;

    public String addItemToCart()
    {
        Item item = catalogBean.findItem( getParamId( "itemId" ) );

        // Start conversation
        if ( conversation.isTransient() )
        {
            cartItems = new ArrayList<>();
            conversation.begin();
        }

        boolean itemFound = false;
        for ( ShoppingCartItem cartItem : cartItems )
        {
            // If item already exists in the shopping cart we just change the quantity
            if ( cartItem.getItem().equals( item ) )
            {
                cartItem.setQuantity( cartItem.getQuantity() + 1 );
                itemFound = true;
            }
        }
        if ( !itemFound )
        // Otherwise it's added to the shopping cart
        {
            cartItems.add( new ShoppingCartItem( item, 1 ) );
        }

        return "showcart.faces";
    }

    public String removeItemFromCart()
    {
        Item item = catalogBean.findItem( getParamId( "itemId" ) );

        for ( ShoppingCartItem cartItem : cartItems )
        {
            if ( cartItem.getItem().equals( item ) )
            {
                cartItems.remove( cartItem );
                return null;
            }
        }

        return null;
    }

    public String updateQuantity()
    {
        return null;
    }

    public String checkout()
    {
        return "confirmorder.faces";
    }

    public String confirmOrder()
    {
        order = orderBean.createOrder( getCustomer(), creditCard, getCartItems() );
        cartItems.clear();

        // Stop conversation
        if ( !conversation.isTransient() )
        {
            conversation.end();
        }

        return "orderconfirmed.faces";
    }

    public List<ShoppingCartItem> getCartItems()
    {
        return cartItems;
    }

    public boolean shoppingCartIsEmpty()
    {
        return getCartItems() == null || getCartItems().size() == 0;
    }

    public Float getTotal()
    {

        if ( cartItems == null || cartItems.isEmpty() )
        {
            return 0f;
        }

        Float total = 0f;

        // Sum up the quantities
        for ( ShoppingCartItem cartItem : cartItems )
        {
            total += ( cartItem.getSubTotal() );
        }
        return total;
    }

    public Customer getCustomer()
    {
        return loggedInCustomer.get();
    }

    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    public void setCreditCard( CreditCard creditCard )
    {
        this.creditCard = creditCard;
    }

    public PurchaseOrder getOrder()
    {
        return order;
    }

    public void setOrder( PurchaseOrder order )
    {
        this.order = order;
    }

    public Conversation getConversation()
    {
        return conversation;
    }

    public CreditCardType[] getCreditCardTypes()
    {
        return CreditCardType.values();
    }
}