package it.demo.quarkus.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.model.Category;
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
@TestSecurity( roles = "user", user = "user" )
public class CategoryResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Test
    public void testCreate()
            throws Exception

    {
        Category category = testCategory();
        final String json = mapper.writeValueAsString( category );
        Pattern pattern = Pattern.compile( "https?:.*/api/categories/\\d+" );
        given().body( json )
               .contentType( ContentType.JSON )
               .post( "/api/categories" )
               .then()
               .statusCode( CREATED.getStatusCode() )
               .header( LOCATION, matchesPattern( pattern ) );
    }

    @Test
    public void testListAllWith3Limits()
    {
        given().when()
               .get( "/api/categories?start=0&max=3" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 3 ) );
    }

    @Test
    public void testFindById()
    {
        given().when()
               .get( "/api/categories/1000" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "id", is( 1000 ) )
               .body( "name", is( "Fish" ) );
    }

    private Category testCategory()
    {
        Category category = new Category();
        category.setName( "test" );
        category.setDescription( "Test Category" );
        category.setVersion( 1 );
        return category;
    }
}