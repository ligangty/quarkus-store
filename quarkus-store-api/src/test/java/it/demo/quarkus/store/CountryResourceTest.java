package it.demo.quarkus.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.model.Country;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@QuarkusTest
public class CountryResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Test
    @TestSecurity( roles = "user", user = "user" )
    public void testCreate()
            throws Exception

    {
        Country country = testCountry();
        final String json = mapper.writeValueAsString( country );
        Pattern pattern = Pattern.compile( "https?:.*/api/countries/\\d+" );
        given().body( json )
               .contentType( ContentType.JSON )
               .post( "/api/countries" )
               .then()
               .statusCode( CREATED.getStatusCode() )
               .header( LOCATION, matchesPattern( pattern ) );
    }

    @Test
    public void testListAllWith3Limits()
    {
        given().when()
               .get( "/api/countries?start=0&max=3" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 3 ) );
    }

    @Test
    public void testFindById()
    {
        given().when()
               .get( "/api/countries/1000" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "id", is( 1000 ) )
               .body( "name", is( "AFGHANISTAN" ) );
    }

    private Country testCountry()
    {
        Country country = new Country();
        country.setName( "Test" );
        country.setIso3( "TES" );
        country.setIsoCode( "TE" );
        country.setPrintableName( "Test Country" );
        country.setVersion( 1 );
        return country;
    }
}