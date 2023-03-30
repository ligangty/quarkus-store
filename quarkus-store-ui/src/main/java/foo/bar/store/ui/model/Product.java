package foo.bar.store.ui.model;

import java.io.Serializable;
import java.util.Objects;

public class Product
        implements Serializable
{

    private Long id;

    private int version;

    private String name;

    private String description;

    private Category category;

    public static final String FIND_BY_CATEGORY_NAME = "Product.findByCategoryName";

    public static final String FIND_ALL = "Product.findAll";

    public Product()
    {
    }

    public Product( String name, String description, Category category )
    {
        this.name = name;
        this.description = description;
        this.category = category;
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

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Category getCategory()
    {
        return this.category;
    }

    public void setCategory( final Category category )
    {
        this.category = category;
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
        Product product = (Product) o;
        return name.equals( product.name ) && description.equals( product.description );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( name, description );
    }

    @Override
    public String toString()
    {
        return name;
    }
}
