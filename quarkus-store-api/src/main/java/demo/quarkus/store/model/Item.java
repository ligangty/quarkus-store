package demo.quarkus.store.model;

import demo.quarkus.store.constraints.NotEmpty;
import demo.quarkus.store.constraints.Price;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Cacheable
@NamedQueries(
        { @NamedQuery( name = Item.FIND_BY_PRODUCT_ID, query = "SELECT i FROM Item i WHERE i.product.id = :productId" ),
                @NamedQuery( name = Item.SEARCH,
                             query = "SELECT i FROM Item i WHERE UPPER(i.name) LIKE :keyword OR UPPER(i.product.name) LIKE :keyword ORDER BY i.product.category.name, i.product.name" ),
                @NamedQuery( name = Item.FIND_ALL, query = "SELECT i FROM Item i" ) } )
public class Item
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

    @Column( name = "image_path" )
    @NotEmpty
    private String imagePath;

    @Column( name = "unit_cost", nullable = false )
    @NotNull
    @Price
    private Float unitCost;

    @ManyToOne( cascade = CascadeType.MERGE )
    @JoinColumn( name = "product_fk", nullable = false )
    @XmlTransient
    private Product product;

    public static final String FIND_BY_PRODUCT_ID = "Item.findByProductId";

    public static final String SEARCH = "Item.search";

    public static final String FIND_ALL = "Item.findAll";

    public Item()
    {
    }

    public Item( String name, Float unitCost, String imagePath, String description, Product product )
    {
        this.name = name;
        this.unitCost = unitCost;
        this.imagePath = imagePath;
        this.description = description;
        this.product = product;
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

    public String getImagePath()
    {
        return imagePath;
    }

    public void setImagePath( String imagePath )
    {
        this.imagePath = imagePath;
    }

    public Float getUnitCost()
    {
        return unitCost;
    }

    public void setUnitCost( Float unitCost )
    {
        this.unitCost = unitCost;
    }

    public Product getProduct()
    {
        return this.product;
    }

    public void setProduct( final Product product )
    {
        this.product = product;
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
        Item item = (Item) o;
        return name.equals( item.name ) && description.equals( item.description );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( name, description );
    }

    @Override
    public String toString()
    {
        return "Item{" + "id=" + id + ", version=" + version + ", name='" + name + '\'' + ", description='"
                + description + '\'' + ", imagePath='" + imagePath + '\'' + ", unitCost=" + unitCost + ", product="
                + product + '}';
    }
}
