package demo.quarkus.store.ui.model;

import demo.quarkus.store.common.auth.UserRole;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Customer
        implements Serializable
{

    private Long id;

    private int version;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String telephone;

    private String email;

    private String login;

    private String uuid;

    private UserRole role;

    @Past
    private Date dateOfBirth;

    private Integer age;

    @Valid
    private Address homeAddress = new Address();

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
