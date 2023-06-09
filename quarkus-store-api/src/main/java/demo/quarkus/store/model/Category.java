package demo.quarkus.store.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@NamedQueries( {
        // TODO fetch doesn't work with GlassFIsh
        // @NamedQuery(name = Category.FIND_BY_NAME, query =
        // "SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.name = :pname"),
        @NamedQuery( name = Category.FIND_BY_NAME, query = "SELECT c FROM Category c WHERE c.name = :pname" ),
        @NamedQuery( name = Category.FIND_ALL, query = "SELECT c FROM Category c" ) } )
public class Category
        implements Serializable
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", updatable = false, nullable = false )
    private Long id;

    @Version
    @Column( name = "version" )
    private int version;

    @Column( length = 30, nullable = false )
    @NotNull
    @Size( min = 1, max = 30 )
    private String name;

    @Column( length = 3000, nullable = false )
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
