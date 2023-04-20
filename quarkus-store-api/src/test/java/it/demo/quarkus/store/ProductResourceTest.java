package it.demo.quarkus.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.model.Category;
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

public class ProductResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Inject
    EntityManager em;

    @Test
    @TestSecurity( roles = "user", user = "user" )
    public void testCreate()
            throws Exception

    {
        Product product = testProduct();
        final String json = mapper.writeValueAsString( product );
        Pattern pattern = Pattern.compile( "https?:.*api/products/\\d+" );
        given().body( json )
               .contentType( ContentType.JSON )
               .post( "api/products" )
               .then()
               .statusCode( CREATED.getStatusCode() )
               .header( LOCATION, matchesPattern( pattern ) );
    }

    @Test
    public void testListAllWith3Limits()
    {
        given().when()
               .get( "api/products?start=0&max=3" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 3 ) );
    }

    @Test
    public void testFindById()
    {
        given().when()
               .get( "api/products/1000" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "id", is( 1000 ) )
               .body( "name", is( "Angelfish" ) );
    }

    private Product testProduct()
    {
        Product product = new Product();
        product.setVersion( 1 );
        product.setName( "Test" );
        product.setDescription( "Test Product" );
        Category category = em.find( Category.class, 1000L );
        product.setCategory( category );
        return product;
    }
}