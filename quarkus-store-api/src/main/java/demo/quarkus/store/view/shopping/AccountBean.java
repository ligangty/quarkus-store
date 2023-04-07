package demo.quarkus.store.view.shopping;

import demo.quarkus.store.model.Customer;
import demo.quarkus.store.service.CustomerService;
import demo.quarkus.store.util.Loggable;
import demo.quarkus.store.view.AbstractBean;
import demo.quarkus.store.view.CatchException;
import demo.quarkus.store.view.LoggedIn;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.LoginException;
import java.io.Serializable;

@Named
@SessionScoped
@Loggable
@CatchException
public class AccountBean
        extends AbstractBean
        implements Serializable
{

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    CustomerService customerService;

    @Inject
    CredentialsBean credentials;

    @LoggedIn
    Customer loggedinCustomer;


    public String doLogin()
            throws LoginException
    {
        if ( credentials.getLogin() == null || "".equals( credentials.getLogin() ) )
        {
            addWarningMessage( "id_filled" );
            return null;
        }
        if ( credentials.getPassword() == null || "".equals( credentials.getPassword() ) )
        {
            addWarningMessage( "pwd_filled" );
            return null;
        }

        // TODO       loginContext.login();
        loggedinCustomer = customerService.findCustomer( credentials.getLogin() );
        return "main.faces";
    }

    public String doCreateNewAccount()
    {

        // Login has to be unique
        if ( customerService.doesLoginAlreadyExist( credentials.getLogin() ) )
        {
            addWarningMessage( "login_exists" );
            return null;
        }

        // Id and password must be filled
        if ( "".equals( credentials.getLogin() ) || "".equals( credentials.getPassword() ) || "".equals(
                credentials.getPassword2() ) )
        {
            addWarningMessage( "id_pwd_filled" );
            return null;
        }
        else if ( !credentials.getPassword().equals( credentials.getPassword2() ) )
        {
            addWarningMessage( "both_pwd_same" );
            return null;
        }

        // Login and password are ok
        loggedinCustomer = new Customer();
        loggedinCustomer.setLogin( credentials.getLogin() );

        return "createaccount.faces";
    }

    public String doCreateCustomer()
    {
        loggedinCustomer = customerService.createCustomer( loggedinCustomer );
        return "main.faces";
    }

    public String doLogout()
    {
        loggedinCustomer = null;
        // Stop conversation
        addInformationMessage( "been_loggedout" );
        return "main.faces";
    }

    public String doUpdateAccount()
    {
        loggedinCustomer = customerService.updateCustomer( loggedinCustomer );
        addInformationMessage( "account_updated" );
        return "showaccount.faces";
    }

    public boolean isLoggedIn()
    {
        return loggedinCustomer != null;
    }

    public Customer getLoggedinCustomer()
    {
        return loggedinCustomer;
    }

    public void setLoggedinCustomer( Customer loggedinCustomer )
    {
        this.loggedinCustomer = loggedinCustomer;
    }
}
