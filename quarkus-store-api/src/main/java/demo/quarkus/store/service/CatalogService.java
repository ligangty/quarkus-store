package demo.quarkus.store.service;

import demo.quarkus.store.model.Category;
import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.Product;
import demo.quarkus.store.util.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Loggable
@ApplicationScoped
public class CatalogService
        implements Serializable
{

    @Inject
    EntityManager em;

    public Category findCategory( @NotNull Long categoryId )
    {
        return em.find( Category.class, categoryId );
    }

    public Category findCategory( @NotNull String categoryName )
    {
        TypedQuery<Category> typedQuery = em.createNamedQuery( Category.FIND_BY_NAME, Category.class );
        typedQuery.setParameter( "pname", categoryName );
        return typedQuery.getSingleResult();
    }

    public List<Category> findAllCategories()
    {
        TypedQuery<Category> typedQuery = em.createNamedQuery( Category.FIND_ALL, Category.class );
        return typedQuery.getResultList();
    }

    public Category createCategory( @NotNull Category category )
    {
        em.persist( category );
        return category;
    }

    public Category updateCategory( @NotNull Category category )
    {
        return em.merge( category );
    }

    public void removeCategory( @NotNull Category category )
    {
        em.remove( em.merge( category ) );
    }

    public void removeCategory( @NotNull Long categoryId )
    {
        removeCategory( findCategory( categoryId ) );
    }

    public List<Product> findProducts( @NotNull String categoryName )
    {
        TypedQuery<Product> typedQuery = em.createNamedQuery( Product.FIND_BY_CATEGORY_NAME, Product.class );
        typedQuery.setParameter( "pname", categoryName );
        return typedQuery.getResultList();
    }

    public Product findProduct( @NotNull Long productId )
    {
        return em.find( Product.class, productId );
    }

    public List<Product> findAllProducts()
    {
        TypedQuery<Product> typedQuery = em.createNamedQuery( Product.FIND_ALL, Product.class );
        return typedQuery.getResultList();
    }

    public Product createProduct( @NotNull Product product )
    {
        if ( product.getCategory() != null && product.getCategory().getId() == null )
        {
            em.persist( product.getCategory() );
        }

        em.persist( product );
        return product;
    }

    public Product updateProduct( @NotNull Product product )
    {
        return em.merge( product );
    }

    public void removeProduct( @NotNull Product product )
    {
        em.remove( em.merge( product ) );
    }

    public void removeProduct( @NotNull Long productId )
    {
        removeProduct( findProduct( productId ) );
    }

    public List<Item> findItems( @NotNull Long productId )
    {
        TypedQuery<Item> typedQuery = em.createNamedQuery( Item.FIND_BY_PRODUCT_ID, Item.class );
        typedQuery.setParameter( "productId", productId );
        return typedQuery.getResultList();
    }

    public Item findItem( @NotNull Long itemId )
    {
        return em.find( Item.class, itemId );
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

    public List<Item> findAllItems()
    {
        TypedQuery<Item> typedQuery = em.createNamedQuery( Item.FIND_ALL, Item.class );
        return typedQuery.getResultList();
    }

    public Item createItem( @NotNull Item item )
    {
        if ( item.getProduct() != null && item.getProduct().getId() == null )
        {
            em.persist( item.getProduct() );
            if ( item.getProduct().getCategory() != null && item.getProduct().getCategory().getId() == null )
            {
                em.persist( item.getProduct().getCategory() );
            }
        }

        em.persist( item );
        return item;
    }

    public Item updateItem( @NotNull Item item )
    {
        return em.merge( item );
    }

    public void removeItem( @NotNull Item item )
    {
        em.remove( em.merge( item ) );
    }

    public void removeItem( @NotNull Long itemId )
    {
        removeItem( findItem( itemId ) );
    }
}
