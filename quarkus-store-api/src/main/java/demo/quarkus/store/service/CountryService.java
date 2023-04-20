package demo.quarkus.store.service;

import demo.quarkus.store.model.Country;
import demo.quarkus.store.util.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Loggable
@ApplicationScoped
public class CountryService
        extends AbstractService<Country>
        implements Serializable
{

    public CountryService()
    {
        super( Country.class );
    }

    @Override
    protected Predicate[] getSearchPredicates( Root<Country> root, Country example )
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        List<Predicate> predicatesList = new ArrayList<>();

        String isoCode = example.getIsoCode();
        if ( isoCode != null && !"".equals( isoCode ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "isoCode" ) ), '%' + isoCode.toLowerCase() + '%' ) );
        }
        String name = example.getName();
        if ( name != null && !"".equals( name ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.get( "name" ) ), '%' + name.toLowerCase() + '%' ) );
        }
        String printableName = example.getPrintableName();
        if ( printableName != null && !"".equals( printableName ) )
        {
            predicatesList.add( builder.like( builder.lower( root.<String>get( "printableName" ) ),
                                              '%' + printableName.toLowerCase() + '%' ) );
        }
        String iso3 = example.getIso3();
        if ( iso3 != null && !"".equals( iso3 ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "iso3" ) ), '%' + iso3.toLowerCase() + '%' ) );
        }
        String numcode = example.getNumcode();
        if ( numcode != null && !"".equals( numcode ) )
        {
            predicatesList.add(
                    builder.like( builder.lower( root.<String>get( "numcode" ) ), '%' + numcode.toLowerCase() + '%' ) );
        }

        return predicatesList.toArray( new Predicate[predicatesList.size()] );
    }
}