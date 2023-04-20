/**
 * Copyright (C) 2023 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.quarkus.store.service;

import demo.quarkus.store.model.CreditCard;
import demo.quarkus.store.model.CreditCardType;
import demo.quarkus.store.model.Customer;
import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.PurchaseOrder;
import demo.quarkus.store.view.LoggedIn;
import demo.quarkus.store.view.shopping.ShoppingCartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Named
@ApplicationScoped
public class ShoppingCartService
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    @LoggedIn
    Instance<Customer> loggedInCustomer;

    @Inject
    ItemService itemService;

    @Inject
    PurchaseOrderService orderBean;

    private final ConcurrentHashMap<String, List<ShoppingCartItem>> cartItems = new ConcurrentHashMap<>();

    private CreditCard creditCard = new CreditCard();

    private PurchaseOrder order;

    public Boolean addItemToCart( final Long itemId )
    {

        if ( getCustomer() == null )
        {
            logger.error( "User is empty. Maybe not logged in?" );
            return false;
        }
        Item item = itemService.findById( itemId );
        if ( item == null )
        {
            logger.warn( "Cannot add item to cart. Item not exists: {}", itemId );
            return false;
        }
        List<ShoppingCartItem> items = getCartItems();
        // Start conversation

        boolean itemFound = false;
        for ( ShoppingCartItem cartItem : items )
        {
            Item it = cartItem.getItem();
            // If item already exists in the shopping cart we just change the quantity
            if ( it.equals( item ) )
            {
                cartItem.setQuantity( cartItem.getQuantity() + 1 );
                itemFound = true;
            }
        }
        if ( !itemFound )
        // Otherwise it's added to the shopping cart
        {
            items.add( new ShoppingCartItem( item, 1 ) );
        }

        return true;
    }

    public boolean removeItemFromCart( final Long itemId )
    {

        if ( getCustomer() == null )
        {
            logger.error( "User is empty. Maybe not logged in?" );
            return false;
        }
        Item item = itemService.findById( itemId );
        if ( item == null )
        {
            logger.warn( "Cannot remove item from cart. Item not exists: {}", itemId );
            return false;
        }
        List<ShoppingCartItem> items = getCartItems();

        for ( ShoppingCartItem cartItem : items )
        {
            if ( cartItem.getItem().equals( item ) )
            {
                items.remove( cartItem );
                return true;
            }
        }

        return true;
    }

    public String confirmOrder()
    {
        order = orderBean.createOrder( getCustomer(), creditCard, getCartItems() );
        getCartItems().clear();

        return "orderconfirmed.faces";
    }

    public List<ShoppingCartItem> getCartItems()
    {
        if ( getCustomer() == null )
        {
            logger.error( "User is empty. Maybe not logged in?" );
            return Collections.emptyList();
        }
        return cartItems.computeIfAbsent( getCustomer().getLogin(), k -> new ArrayList<>() );
    }

    public boolean shoppingCartIsEmpty()
    {
        return getCartItems() == null || getCartItems().size() == 0;
    }

    public Float getTotal()
    {
        if ( getCustomer() == null )
        {
            logger.error( "User is empty. Maybe not logged in?" );
            return 0f;
        }
        List<ShoppingCartItem> items = cartItems.get( getCustomer().getLogin() );

        if ( items == null || items.isEmpty() )
        {
            return 0f;
        }

        Float total = 0f;

        // Sum up the quantities
        for ( ShoppingCartItem cartItem : items )
        {
            total += ( cartItem.getSubTotal() );
        }
        return total;
    }

    public Customer getCustomer()
    {
        return loggedInCustomer.get();
    }

    public String checkout()
    {
        return "confirmorder.faces";
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

    public CreditCardType[] getCreditCardTypes()
    {
        return CreditCardType.values();
    }

}
