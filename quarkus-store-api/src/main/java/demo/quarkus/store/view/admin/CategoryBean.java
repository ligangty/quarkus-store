package demo.quarkus.store.view.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

import demo.quarkus.store.model.Category;
import demo.quarkus.store.util.Loggable;

/**
 * Backing bean for Category entities.
 * <p/>
 * This class provides CRUD functionality for all Category entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
@Loggable
public class CategoryBean
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    /*
     * Support creating and retrieving Category entities
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

    private Category category;

    public Category getCategory()
    {
        return this.category;
    }

    public void setCategory( Category category )
    {
        this.category = category;
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

    public void retrieve()
    {


        if ( this.conversation.isTransient() )
        {
            this.conversation.begin();
            this.conversation.setTimeout( 1800000L );
        }

        if ( this.id == null )
        {
            this.category = this.example;
        }
        else
        {
            this.category = findById( getId() );
        }
    }

    public Category findById( Long id )
    {

        return this.entityManager.find( Category.class, id );
    }

    /*
     * Support updating and deleting Category entities
     */

    public String update()
    {
        this.conversation.end();

        try
        {
            if ( this.id == null )
            {
                this.entityManager.persist( this.category );
                return "search?faces-redirect=true";
            }
            else
            {
                this.entityManager.merge( this.category );
                return "view?faces-redirect=true&id=" + this.category.getId();
            }
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public String delete()
    {
        this.conversation.end();

        try
        {
            Category deletableEntity = findById( getId() );

            this.entityManager.remove( deletableEntity );
            this.entityManager.flush();
            return "search?faces-redirect=true";
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    /*
     * Support searching Category entities with pagination
     */

    private int page;

    private long count;

    private List<Category> pageItems;

    private Category example = new Category();

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

    public Category getExample()
    {
        return this.example;
    }

    public void setExample( Category example )
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
        Root<Category> root = countCriteria.from( Category.class );
        countCriteria = countCriteria.select( builder.count( root ) ).where( getSearchPredicates( root ) );
        this.count = this.entityManager.createQuery( countCriteria ).getSingleResult();

        // Populate this.pageItems

        CriteriaQuery<Category> criteria = builder.createQuery( Category.class );
        root = criteria.from( Category.class );
        TypedQuery<Category> query =
                this.entityManager.createQuery( criteria.select( root ).where( getSearchPredicates( root ) ) );
        query.setFirstResult( this.page * getPageSize() ).setMaxResults( getPageSize() );
        this.pageItems = query.getResultList();
    }

    private Predicate[] getSearchPredicates( Root<Category> root )
    {

        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<Predicate>();

        String name = this.example.getName();
        if ( name != null && !"".equals( name ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "name" ) ), '%' + name.toLowerCase() + '%' ) );
        }
        String description = this.example.getDescription();
        if ( description != null && !"".equals( description ) )
        {
            predicatesList.add( builder.like( builder.lower( root.<String>get( "description" ) ),
                                              '%' + description.toLowerCase() + '%' ) );
        }

        return predicatesList.toArray( new Predicate[predicatesList.size()] );
    }

    public List<Category> getPageItems()
    {
        return this.pageItems;
    }

    public long getCount()
    {
        return this.count;
    }

    /*
     * Support listing and POSTing back Category entities (e.g. from inside an
     * HtmlSelectOneMenu)
     */

    public List<Category> getAll()
    {

        CriteriaQuery<Category> criteria = this.entityManager.getCriteriaBuilder().createQuery( Category.class );
        return this.entityManager.createQuery( criteria.select( criteria.from( Category.class ) ) ).getResultList();
    }


    /*
     * Support adding children to bidirectional, one-to-many tables
     */

    private Category add = new Category();

    public Category getAdd()
    {
        return this.add;
    }

    public Category getAdded()
    {
        Category added = this.add;
        this.add = new Category();
        return added;
    }
}
