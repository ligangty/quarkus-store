package demo.quarkus.store.service;

import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.Product;
import demo.quarkus.store.util.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Loggable
@ApplicationScoped
public class ItemService
        extends AbstractService<Item>
        implements Serializable
{
    @Inject
    EntityManager em;

    public ItemService()
    {
        super( Item.class );
    }

    @Override
    protected Predicate[] getSearchPredicates( Root<Item> root, Item example )
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<>();

        String name = example.getName();
        if ( name != null && !"".equals( name ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "name" ) ), '%' + name.toLowerCase() + '%' ) );
        }
        String description = example.getDescription();
        if ( description != null && !"".equals( description ) )
        {
            predicatesList.add( builder.like( builder.lower( root.get( "description" ) ),
                                              '%' + description.toLowerCase() + '%' ) );
        }
        String imagePath = example.getImagePath();
        if ( imagePath != null && !"".equals( imagePath ) )
        {
            predicatesList.add( builder.like( builder.lower( root.get( "imagePath" ) ),
                                              '%' + imagePath.toLowerCase() + '%' ) );
        }
        Product product = example.getProduct();
        if ( product != null )
        {
            predicatesList.add( builder.equal( root.get( "product" ), product ) );
        }

        return predicatesList.toArray( new Predicate[0] );
    }

    public List<Item> findItemsByProduct( Long productId )
    {
        TypedQuery<Item> typedQuery = em.createNamedQuery( Item.FIND_BY_PRODUCT_ID, Item.class );
        typedQuery.setParameter( "productId", productId );
        return typedQuery.getResultList();
    }

    public List<Item> searchItems( String keyword )
    {
        if ( keyword == null )
        {
            keyword = "";
        }

        TypedQuery<Item> typedQuery = em.createNamedQuery( Item.SEARCH, Item.class );
        typedQuery.setParameter( "keyword", "%" + keyword.toUpperCase() + "%" );
        return typedQuery.getResultList();
    }

}

