package it.demo.quarkus.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.model.Item;
import demo.quarkus.store.model.Product;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@QuarkusTest
@TestSecurity( roles = "user", user = "user" )
public class ItemResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Inject
    EntityManager em;

    @Test
    public void testCreate()
            throws Exception

    {
        Item item = testItem();
        final String json = mapper.writeValueAsString( item );
        Pattern pattern = Pattern.compile( "https?:.*api/items/\\d+" );
        given().body( json )
               .contentType( ContentType.JSON )
               .post( "api/items" )
               .then()
               .statusCode( CREATED.getStatusCode() )
               .header( LOCATION, matchesPattern( pattern ) );
    }

    @Test
    public void testListAllWith3Limits()
    {
        given().when()
               .get( "api/items?start=0&max=3" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 3 ) );
    }

    @Test
    public void testFindById()
    {
        given().when()
               .get( "api/items/1000" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "id", is( 1000 ) )
               .body( "name", is( "Large" ) );
    }

    private Item testItem()
    {
        Item item = new Item();
        item.setVersion( 1 );
        item.setName( "Test" );
        item.setDescription( "Test Item" );
        item.setUnitCost( 1.00F );
        Product product = em.find( Product.class, 1000L );
        item.setProduct( product );
        return item;
    }
}