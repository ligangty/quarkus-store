package foo.bar.store.ui.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class Country
        implements Serializable
{

    private Long id;

    private int version;

    @NotNull
    @Size( min = 2, max = 2 )
    private String isoCode;

    @NotNull
    @Size( min = 2, max = 80 )
    private String name;

    @NotNull
    @Size( min = 2, max = 80 )
    private String printableName;

    @NotNull
    @Size( min = 3, max = 3 )
    private String iso3;

    @NotNull
    @Size( min = 3, max = 3 )
    private String numcode;

    public Country()
    {
    }

    public Country( String isoCode, String name, String printableName, String iso3, String numcode )
    {
        this.isoCode = isoCode;
        this.name = name;
        this.printableName = printableName;
        this.iso3 = iso3;
        this.numcode = numcode;
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

    public String getIsoCode()
    {
        return isoCode;
    }

    public void setIsoCode( String isoCode )
    {
        this.isoCode = isoCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getPrintableName()
    {
        return printableName;
    }

    public void setPrintableName( String printableName )
    {
        this.printableName = printableName;
    }

    public String getIso3()
    {
        return iso3;
    }

    public void setIso3( String iso3 )
    {
        this.iso3 = iso3;
    }

    public String getNumcode()
    {
        return numcode;
    }

    public void setNumcode( String numcode )
    {
        this.numcode = numcode;
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
        Country country = (Country) o;
        return isoCode.equals( country.isoCode );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( isoCode );
    }

    @Override
    public String toString()
    {
        return name;
    }
}
