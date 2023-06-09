package demo.quarkus.store.service;

import demo.quarkus.store.exceptions.ValidationException;
import demo.quarkus.store.model.Address;
import demo.quarkus.store.model.Country;
import demo.quarkus.store.model.CreditCard;
import demo.quarkus.store.model.Customer;
import demo.quarkus.store.model.OrderLine;
import demo.quarkus.store.model.PurchaseOrder;
import demo.quarkus.store.util.Loggable;
import demo.quarkus.store.view.shopping.ShoppingCartItem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Loggable
@Named
@ApplicationScoped
public class PurchaseOrderService
        extends AbstractService<PurchaseOrder>
        implements Serializable
{

    public PurchaseOrder createOrder( @NotNull Customer customer, @NotNull CreditCard creditCard,
                                      final List<ShoppingCartItem> cartItems )
    {

        // OMake sure the object is valid
        if ( cartItems == null || cartItems.size() == 0 )
        {
            throw new ValidationException( "Shopping cart is empty" ); // TODO exception bean validation
        }

        // Creating the order
        Address deliveryAddress = customer.getHomeAddress();
        Country country = entityManager.find( Country.class, customer.getHomeAddress().getCountry().getId() );
        deliveryAddress.setCountry( country );
        PurchaseOrder order = new PurchaseOrder( entityManager.merge( customer ), creditCard, deliveryAddress );

        // From the shopping cart we create the order lines
        Set<OrderLine> orderLines = new HashSet<>();

        for ( ShoppingCartItem cartItem : cartItems )
        {
            orderLines.add( new OrderLine( cartItem.getQuantity(), entityManager.merge( cartItem.getItem() ) ) );
        }
        order.setOrderLines( orderLines );

        // Persists the object to the database
        entityManager.persist( order );

        return order;
    }

    public PurchaseOrder findOrder( @NotNull Long orderId )
    {
        return entityManager.find( PurchaseOrder.class, orderId );
    }

    public List<PurchaseOrder> findAllOrders()
    {
        TypedQuery<PurchaseOrder> typedQuery =
                entityManager.createNamedQuery( PurchaseOrder.FIND_ALL, PurchaseOrder.class );
        return typedQuery.getResultList();
    }

    public void removeOrder( @NotNull PurchaseOrder order )
    {
        entityManager.remove( entityManager.merge( order ) );
    }

    @Override
    protected Predicate[] getSearchPredicates( Root<PurchaseOrder> root, PurchaseOrder example )
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<>();

        String street1 = example.getCustomer().getHomeAddress().getStreet1();
        if ( street1 != null && !"".equals( street1 ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "street1" ) ), '%' + street1.toLowerCase() + '%' ) );
        }
        String street2 = example.getCustomer().getHomeAddress().getStreet2();
        if ( street2 != null && !"".equals( street2 ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "street2" ) ), '%' + street2.toLowerCase() + '%' ) );
        }
        String city = example.getCustomer().getHomeAddress().getCity();
        if ( city != null && !"".equals( city ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "city" ) ), '%' + city.toLowerCase() + '%' ) );
        }
        String state = example.getCustomer().getHomeAddress().getState();
        if ( state != null && !"".equals( state ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "state" ) ), '%' + state.toLowerCase() + '%' ) );
        }
        String zipcode = example.getCustomer().getHomeAddress().getZipcode();
        if ( zipcode != null && !"".equals( zipcode ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "zipcode" ) ), '%' + zipcode.toLowerCase() + '%' ) );
        }

        return predicatesList.toArray( new Predicate[0] );
    }
}
