package demo.quarkus.store.service;

import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.OrderLine;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import demo.quarkus.store.util.Loggable;

@Loggable
public class OrderLineService
        extends AbstractService<OrderLine>
        implements Serializable
{

    public OrderLineService()
    {
        super( OrderLine.class );
    }

    @Override
    protected Predicate[] getSearchPredicates( Root<OrderLine> root, OrderLine example )
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        Integer quantity = example.getQuantity();
        if ( quantity != null && quantity != 0 )
        {
            predicatesList.add( builder.equal( root.get( "quantity" ), quantity ) );
        }
        Item item = example.getItem();
        if ( item != null )
        {
            predicatesList.add( builder.equal( root.get( "item" ), item ) );
        }

        return predicatesList.toArray( new Predicate[0] );
    }
}