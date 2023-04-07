package demo.quarkus.store.ui.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class Category
        implements Serializable
{

    private Long id;

    private int version;

    @NotNull
    @Size( min = 1, max = 30 )
    private String name;

    @NotNull
    @Size( max = 3000 )
    private String description;

    public static final String FIND_BY_NAME = "Category.findByName";

    public static final String FIND_ALL = "Category.findAll";

    public Category()
    {
    }

    public Category( String name, String description )
    {
        this.name = name;
        this.description = description;
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
        Category category = (Category) o;
        return name.equals( category.name );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( name );
    }

    @Override
    public String toString()
    {
        return name;
    }
}
