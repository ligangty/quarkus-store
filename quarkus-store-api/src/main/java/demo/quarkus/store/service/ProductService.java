package demo.quarkus.store.service;

import demo.quarkus.store.model.Category;
import demo.quarkus.store.model.Product;
import demo.quarkus.store.util.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Loggable
@ApplicationScoped
public class ProductService
        extends AbstractService<Product>
        implements Serializable
{

    public ProductService()
    {
        super( Product.class );
    }

    @Override
    protected Predicate[] getSearchPredicates( Root<Product> root, Product example )
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<>();

        String name = example.getName();
        if ( name != null && !"".equals( name ) )
        {
            predicatesList.add( builder.like( builder.lower( root.get( "name" ) ), '%' + name.toLowerCase() + '%' ) );
        }
        String description = example.getDescription();
        if ( description != null && !"".equals( description ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "description" ) ), '%' + description.toLowerCase() + '%' ) );
        }
        Category category = example.getCategory();
        if ( category != null )
        {
            predicatesList.add( builder.equal( root.get( "category" ), category ) );
        }

        return predicatesList.toArray( new Predicate[0] );
    }

    public List<Product> findProductsByCategory( @QueryParam( "category" ) final String categoryName )
    {
        TypedQuery<Product> typedQuery = entityManager.createNamedQuery( Product.FIND_BY_CATEGORY_NAME, Product.class );
        typedQuery.setParameter( "pname", categoryName );
        return typedQuery.getResultList();
    }
}