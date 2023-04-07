package demo.quarkus.store.ui.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Customer
        implements Serializable
{

    private Long id;

    private int version;

    @NotNull
    @Size( min = 2, max = 50 )
    private String firstName;

    @NotNull
    @Size( min = 2, max = 50 )
    private String lastName;

    private String telephone;

    private String email;

    private String login;

    @NotNull
    @Size( min = 1, max = 256 )
    private String password;

    @Size( min = 1, max = 256 )
    private String uuid;

    private UserRole role;

    @Past
    private Date dateOfBirth;

    private Integer age;

    @Valid
    private Address homeAddress = new Address();

    public static final String FIND_BY_LOGIN = "Customer.findByLogin";

    public static final String FIND_BY_LOGIN_PASSWORD = "Customer.findByLoginAndPassword";

    public static final String FIND_ALL = "Customer.findAll";

    public static final String FIND_BY_EMAIL = "Customer.findByEmail";

    public static final String FIND_BY_UUID = "Customer.findByUUID";


    public Customer()
    {
    }

    public Customer( String firstName, String lastName, String login, String plainTextPassword, String email,
                     Address address )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = digestPassword( plainTextPassword );
        this.email = email;
        this.homeAddress = address;
        this.dateOfBirth = new Date();
    }

    /**
     * This method calculates the age of the customer
     */
    public void calculateAge()
    {
        if ( dateOfBirth == null )
        {
            age = null;
            return;
        }

        Calendar birth = new GregorianCalendar();
        birth.setTime( dateOfBirth );
        Calendar now = new GregorianCalendar();
        now.setTime( new Date() );
        int adjust = 0;
        if ( now.get( Calendar.DAY_OF_YEAR ) - birth.get( Calendar.DAY_OF_YEAR ) < 0 )
        {
            adjust = -1;
        }
        age = now.get( Calendar.YEAR ) - birth.get( Calendar.YEAR ) + adjust;
    }

    private void digestPassword()
    {
        password = digestPassword( password );
    }

    /**
     * Digest password with <code>SHA-256</code> then encode it with Base64.
     *
     * @param plainTextPassword the password to digest and encode
     * @return digested password
     */
    public String digestPassword( String plainTextPassword )
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance( "SHA-256" );
            md.update( plainTextPassword.getBytes( "UTF-8" ) );
            byte[] passwordDigest = md.digest();
            return Base64.getEncoder().encodeToString( passwordDigest );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( "Exception encoding password", e );
        }
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

    public String getLogin()
    {
        return login;
    }

    public void setLogin( String login )
    {
        this.login = login;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole( UserRole role )
    {
        this.role = role;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( String uuid )
    {
        this.uuid = uuid;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone( String telephone )
    {
        this.telephone = telephone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth )
    {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge()
    {
        return age;
    }

    public Address getHomeAddress()
    {
        return homeAddress;
    }

    public void setHomeAddress( Address homeAddress )
    {
        this.homeAddress = homeAddress;
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
        Customer customer = (Customer) o;
        return login.equals( customer.login );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( login );
    }

    @Override
    public String toString()
    {
        return firstName + ' ' + lastName + " (" + login + ")";
    }
}
