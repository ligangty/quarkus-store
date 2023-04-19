package demo.quarkus.store.view.admin;

import demo.quarkus.store.model.Country;
import demo.quarkus.store.util.Loggable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for Country entities.
 * <p/>
 * This class provides CRUD functionality for all Country entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
@Loggable
public class CountryBean
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Country entities
     */

    private Long id;

    public Long getId()
    {
        return this.id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    private Country country;

    public Country getCountry()
    {
        return this.country;
    }

    public void setCountry( Country country )
    {
        this.country = country;
    }

    @Inject
    Conversation conversation;

    @PersistenceContext( unitName = "quakusStore", type = PersistenceContextType.EXTENDED )
    private EntityManager entityManager;

    public String create()
    {

        this.conversation.begin();
        this.conversation.setTimeout( 1800000L );
        return "create?faces-redirect=true";
    }


    public Country findById( Long id )
    {

        return this.entityManager.find( Country.class, id );
    }

    /*
     * Support updating and deleting Country entities
     */

    public String update()
    {
        this.conversation.end();

        try
        {
            if ( this.id == null )
            {
                this.entityManager.persist( this.country );
                return "search?faces-redirect=true";
            }
            else
            {
                this.entityManager.merge( this.country );
                return "view?faces-redirect=true&id=" + this.country.getId();
            }
        }
        catch ( Exception e )
        {
            return null;
        }
    }


    /*
     * Support searching Country entities with pagination
     */

    private int page;

    private long count;

    private List<Country> pageItems;

    private Country example = new Country();

    public int getPage()
    {
        return this.page;
    }

    public void setPage( int page )
    {
        this.page = page;
    }

    public int getPageSize()
    {
        return 10;
    }

    public Country getExample()
    {
        return this.example;
    }

    public void setExample( Country example )
    {
        this.example = example;
    }

    public String search()
    {
        this.page = 0;
        return null;
    }

    public void paginate()
    {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

        // Populate this.count

        CriteriaQuery<Long> countCriteria = builder.createQuery( Long.class );
        Root<Country> root = countCriteria.from( Country.class );
        countCriteria = countCriteria.select( builder.count( root ) ).where( getSearchPredicates( root ) );
        this.count = this.entityManager.createQuery( countCriteria ).getSingleResult();

        // Populate this.pageItems

        CriteriaQuery<Country> criteria = builder.createQuery( Country.class );
        root = criteria.from( Country.class );
        TypedQuery<Country> query =
                this.entityManager.createQuery( criteria.select( root ).where( getSearchPredicates( root ) ) );
        query.setFirstResult( this.page * getPageSize() ).setMaxResults( getPageSize() );
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates( Root<Country> root )
    {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String isoCode = this.example.getIsoCode();
        if ( isoCode != null && !"".equals( isoCode ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "isoCode" ) ), '%' + isoCode.toLowerCase() + '%' ) );
        }
        String name = this.example.getName();
        if ( name != null && !"".equals( name ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "name" ) ), '%' + name.toLowerCase() + '%' ) );
        }
        String printableName = this.example.getPrintableName();
        if ( printableName != null && !"".equals( printableName ) )
        {
            predicatesList.add( builder.like( builder.lower( root.<String>get( "printableName" ) ),
                                              '%' + printableName.toLowerCase() + '%' ) );
        }
        String iso3 = this.example.getIso3();
        if ( iso3 != null && !"".equals( iso3 ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "iso3" ) ), '%' + iso3.toLowerCase() + '%' ) );
        }
        String numcode = this.example.getNumcode();
        if ( numcode != null && !"".equals( numcode ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "numcode" ) ), '%' + numcode.toLowerCase() + '%' ) );
        }

        return predicatesList.toArray( new Predicate[predicatesList.size()] );
    }

    public List<Country> getPageItems()
    {
        return this.pageItems;
    }

    public long getCount()
    {
        return this.count;
    }

    /*
     * Support listing and POSTing back Country entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */

    public List<Country> getAll()
    {

        CriteriaQuery<Country> criteria = this.entityManager.getCriteriaBuilder().createQuery( Country.class );
        return this.entityManager.createQuery( criteria.select( criteria.from( Country.class ) ) ).getResultList();
    }


    /*
     * Support adding children to bidirectional, one-to-many tables
     */

    private Country add = new Country();

    public Country getAdd()
    {
        return this.add;
    }

    public Country getAdded()
    {
        Country added = this.add;
        this.add = new Country();
        return added;
    }
}
