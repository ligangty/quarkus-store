package foo.bar.store.ui.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class CreditCard
        implements Serializable
{

    @NotNull
    @Size( min = 1, max = 30 )
    private String creditCardNumber;

    @NotNull
    private CreditCardType creditCardType;

    @NotNull
    @Size( min = 1, max = 5 )
    private String creditCardExpDate;

    public CreditCard()
    {
    }

    public CreditCard( String creditCardNumber, CreditCardType creditCardType, String creditCardExpDate )
    {
        this.creditCardNumber = creditCardNumber;
        this.creditCardType = creditCardType;
        this.creditCardExpDate = creditCardExpDate;
    }

    public String getCreditCardNumber()
    {
        return creditCardNumber;
    }

    public void setCreditCardNumber( String creditCardNumber )
    {
        this.creditCardNumber = creditCardNumber;
    }

    public CreditCardType getCreditCardType()
    {
        return creditCardType;
    }

    public void setCreditCardType( CreditCardType creditCardType )
    {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardExpDate()
    {
        return creditCardExpDate;
    }

    public void setCreditCardExpDate( String creditCardExpDate )
    {
        this.creditCardExpDate = creditCardExpDate;
    }

    @Override
    public final boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof CreditCard ) )
        {
            return false;
        }
        CreditCard that = (CreditCard) o;
        return Objects.equals( creditCardNumber, that.creditCardNumber ) && Objects.equals( creditCardType,
                                                                                            that.creditCardType )
                && Objects.equals( creditCardExpDate, that.creditCardExpDate );
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash( creditCardNumber, creditCardType, creditCardExpDate );
    }

    @Override
    public String toString()
    {
        return "CreditCard{" + "creditCardNumber='" + creditCardNumber + '\'' + ", creditCardType=" + creditCardType
                + ", creditCardExpDate='" + creditCardExpDate + '\'' + '}';
    }
}
