package demo.quarkus.store.view.shopping;

import demo.quarkus.store.model.Item;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ShoppingCartItem
{

    @NotNull
    private Item item;

    @NotNull
    @Min( 1 )
    private Integer quantity;

    public ShoppingCartItem( Item item, Integer quantity )
    {
        this.item = item;
        this.quantity = quantity;
    }

    public Float getSubTotal()
    {
        return item.getUnitCost() * quantity;
    }

    public Item getItem()
    {
        return item;
    }

    public void setItem( Item item )
    {
        this.item = item;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        ShoppingCartItem cartItem = (ShoppingCartItem) o;

        if ( !item.equals( cartItem.item ) )
        {
            return false;
        }
        if ( !quantity.equals( cartItem.quantity ) )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = item.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append( "CartItem" );
        sb.append( "{item='" ).append( item ).append( '\'' );
        sb.append( ", quantity='" ).append( quantity ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }
}