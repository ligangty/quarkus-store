package demo.quarkus.store.model;

import demo.quarkus.store.constraints.Email;
import demo.quarkus.store.constraints.Login;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
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

import static java.nio.charset.StandardCharsets.UTF_8;

@Entity
@NamedQueries(
        { @NamedQuery( name = Customer.FIND_BY_LOGIN, query = "SELECT c FROM Customer c WHERE c.login = :login" ),
                @NamedQuery( name = Customer.FIND_BY_UUID, query = "SELECT c FROM Customer c WHERE c.uuid = :uuid" ),
                @NamedQuery( name = Customer.FIND_ALL, query = "SELECT c FROM Customer c" ) } )
public class Customer
        implements Serializable
{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "id", updatable = false, nullable = false )
    private Long id;

    @Version
    @Column( name = "version" )
    private int version;

    @Column
    private String telephone;

    @Column( length = 10, nullable = false )
    @Login
    private String login;

    @Column( length = 256 )
    @Size( min = 1, max = 256 )
    private String uuid;

    private UserRole role;

    @Column( name = "date_of_birth" )
    @Temporal( TemporalType.DATE )
    @Past
    private Date dateOfBirth;

    @NotNull
    @Transient
    private String firstName;

    @NotNull
    @Transient
    private String lastName;

    @Email
    @Transient
    private String email;

    @Transient
    private Integer age;

    @Embedded
    @Valid
    private Address homeAddress = new Address();

    public static final String FIND_BY_LOGIN = "Customer.findByLogin";

    //    public static final String FIND_BY_LOGIN_PASSWORD = "Customer.findByLoginAndPassword";

    public static final String FIND_ALL = "Customer.findAll";

    public static final String FIND_BY_EMAIL = "Customer.findByEmail";

    public static final String FIND_BY_UUID = "Customer.findByUUID";

    public Customer()
    {
    }

    public Customer( String firstName, String lastName, String login, String email, Address address )
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.homeAddress = address;
        this.dateOfBirth = new Date();
    }


    /**
     * This method calculates the age of the customer
     */
    @PostLoad
    @PostPersist
    @PostUpdate
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
            md.update( plainTextPassword.getBytes( UTF_8 ) );
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
