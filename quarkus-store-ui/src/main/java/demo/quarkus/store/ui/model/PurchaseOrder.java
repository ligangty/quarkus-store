package demo.quarkus.store.ui.model;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PurchaseOrder
        implements Serializable
{

    private Long id;

    private int version;

    private Date orderDate;

    private Float totalWithoutVat;

    private Float vatRate;

    private Float vat;

    private Float totalWithVat;

    private Float discountRate;

    private Float discount;

    private Float total;

    private Customer customer;

    private Set<OrderLine> orderLines = new HashSet<OrderLine>();

    @Valid
    private Address deliveryAddress = new Address();

    @Valid
    private CreditCard creditCard = new CreditCard();

    public static final String FIND_ALL = "Order.findAll";

    public PurchaseOrder()
    {
    }

    public PurchaseOrder( Customer customer, CreditCard creditCard, Address deliveryAddress )
    {
        this.customer = customer;
        this.creditCard = creditCard;
        this.deliveryAddress = deliveryAddress;
    }

    private void setDefaultData()
    {
        orderDate = new Date();
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

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate( Date orderDate )
    {
        this.orderDate = orderDate;
    }

    public Float getTotalWithoutVat()
    {
        return totalWithoutVat;
    }

    public void setTotalWithoutVat( Float totalWithoutVat )
    {
        this.totalWithoutVat = totalWithoutVat;
    }

    public Float getVatRate()
    {
        return vatRate;
    }

    public void setVatRate( Float vatRate )
    {
        this.vatRate = vatRate;
    }

    public Float getVat()
    {
        return vat;
    }

    public void setVat( Float vat )
    {
        this.vat = vat;
    }

    public Float getTotalWithVat()
    {
        return totalWithVat;
    }

    public void setTotalWithVat( Float totalWithVat )
    {
        this.totalWithVat = totalWithVat;
    }

    public Float getDiscountRate()
    {
        return discountRate;
    }

    public void setDiscountRate( Float discountRate )
    {
        this.discountRate = discountRate;
    }

    public Float getDiscount()
    {
        return discount;
    }

    public void setDiscount( Float discount )
    {
        this.discount = discount;
    }

    public Float getTotal()
    {
        return total;
    }

    public void setTotal( Float total )
    {
        this.total = total;
    }

    public Customer getCustomer()
    {
        return this.customer;
    }

    public void setCustomer( final Customer customer )
    {
        this.customer = customer;
    }

    public Set<OrderLine> getOrderLines()
    {
        return this.orderLines;
    }

    public void setOrderLines( final Set<OrderLine> orderLines )
    {
        this.orderLines = orderLines;
    }

    public Address getDeliveryAddress()
    {
        return deliveryAddress;
    }

    public void setDeliveryAddress( Address deliveryAddress )
    {
        this.deliveryAddress = deliveryAddress;
    }

    public CreditCard getCreditCard()
    {
        return creditCard;
    }

    public void setCreditCard( CreditCard creditCard )
    {
        this.creditCard = creditCard;
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
        PurchaseOrder that = (PurchaseOrder) o;
        return orderDate.equals( that.orderDate ) && customer.equals( that.customer );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( orderDate, customer );
    }

    @Override
    public String toString()
    {
        return "PurchaseOrder{" + "id=" + id + ", version=" + version + ", orderDate=" + orderDate
                + ", totalWithoutVat=" + totalWithoutVat + ", vatRate=" + vatRate + ", vat=" + vat + ", totalWithVat="
                + totalWithVat + ", discountRate=" + discountRate + ", discount=" + discount + ", total=" + total
                + ", customer=" + customer + ", orderLines=" + orderLines + ", deliveryAddress=" + deliveryAddress
                + ", creditCard=" + creditCard + '}';
    }
}
