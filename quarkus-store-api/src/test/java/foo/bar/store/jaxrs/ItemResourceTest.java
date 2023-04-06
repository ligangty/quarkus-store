package foo.bar.store.jaxrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ItemResourceTest
{
    @Inject
    ObjectMapper mapper;

    @Test
    public void testFindById()
    {
    }

    @Test
    public void testListAllWith10Items()
    {
        given().when().get( "/api/items?start=1&max=10" ).then().statusCode( 200 ).body( "size()", is( 10 ) );
    }
}