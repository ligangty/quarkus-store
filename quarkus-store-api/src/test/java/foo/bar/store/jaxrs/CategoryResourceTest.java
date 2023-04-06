package foo.bar.store.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import foo.bar.store.model.Category;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.LOCATION;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.MatchesPattern.matchesPattern;

@QuarkusTest
public class CategoryResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Test
    @TestSecurity( roles = "user", user = "user" )
    public void testCreate()
            throws Exception

    {
        Category category = new Category();
        category.setName( "test" );
        category.setDescription( "Test Category" );
        category.setVersion( 1 );
        final String json = mapper.writeValueAsString( category );
        Pattern pattern = Pattern.compile( "https?:.*/api/categories/[0-9]{0,3}" );
        given().body( json )
               .contentType( ContentType.JSON )
               .post( "/api/categories" )
               .then()
               .statusCode( Response.Status.CREATED.getStatusCode() )
               .header( LOCATION, matchesPattern( pattern ) );
    }

    @Test
    public void testListAllWith10Items()
    {
        given().when().get( "/api/categories?start=0&max=3" ).then().statusCode( 200 ).body( "size()", is( 3 ) );
    }
}