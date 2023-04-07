package demo.quarkus.store.ui.model;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;

public class OrderLine
        implements Serializable
{

    private Long id;

    private int version;

    @Min( 1 )
    private Integer quantity;

    private Item item;

    public OrderLine()
    {
    }

    public OrderLine( Integer quantity, Item item )
    {
        this.quantity = quantity;
        this.item = item;
    }

    public Float getSubTotal()
    {
        return item.getUnitCost() * quantity;
    }



    public Long getId()
    {
        return this.id;
    }

    public void setId( final Long id )
    {
        this.id = id;
    }

    public int getVersion()
    {
        return this.version;
    }

    public void setVersion( final int version )
    {
        this.version = version;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity( Integer quantity )
    {
        this.quantity = quantity;
    }

    public Item getItem()
    {
        return this.item;
    }

    public void setItem( final Item item )
    {
        this.item = item;
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
        OrderLine orderLine = (OrderLine) o;
        return quantity.equals( orderLine.quantity ) && item.equals( orderLine.item );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( quantity, item );
    }

    @Override
    public String toString()
    {
        return "OrderLine{" + "id=" + id + ", version=" + version + ", quantity=" + quantity + ", item=" + item + '}';
    }
}
