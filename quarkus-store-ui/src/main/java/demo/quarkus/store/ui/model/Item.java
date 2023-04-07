package demo.quarkus.store.ui.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

public class Item
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

    private String imagePath;

    @NotNull
    private Float unitCost;

    @XmlTransient
    private Product product;

    // ======================================
    // = Constants =
    // ======================================

    public static final String FIND_BY_PRODUCT_ID = "Item.findByProductId";

    public static final String SEARCH = "Item.search";

    public static final String FIND_ALL = "Item.findAll";

    // ======================================
    // = Constructors =
    // ======================================

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

    // ======================================
    // = Getters & setters =
    // ======================================

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

    // ======================================
    // = Methods hash, equals, toString =
    // ======================================

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
