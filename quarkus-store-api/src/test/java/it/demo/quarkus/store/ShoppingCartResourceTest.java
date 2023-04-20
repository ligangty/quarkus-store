/**
 * Copyright (C) 2023 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.demo.quarkus.store;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import it.demo.quarkus.store.matchers.DebugMatcher;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ShoppingCartResourceTest
{
    @Test
    @TestSecurity( user = "user", roles = "user" )
    public void testAll()
    {
        given().when().get( "api/cart/addItem/1000" ).then().statusCode( OK.getStatusCode() );
        given().when().get( "api/cart/addItem/100000000" ).then().statusCode( BAD_REQUEST.getStatusCode() );

        given().when()
               .get( "api/cart/items" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "[0]", new DebugMatcher<>() )
               .body( "size()", is( 1 ) )
               .body( "[0].item.id", is( 1000 ) )
               .body( "[0].quantity", is( 1 ) );

        given().when().get( "api/cart/addItem/1001" ).then().statusCode( OK.getStatusCode() );

        given().when()
               .get( "api/cart/items" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 2 ) )
               .body( "[1].item.id", is( 1001 ) )
               .body( "[1].quantity", is( 1 ) );

        given().when().get( "api/cart/addItem/1000" ).then().statusCode( OK.getStatusCode() );
        given().when()
               .get( "api/cart/items" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "size()", is( 2 ) )
               .body( "[0].item.id", is( 1000 ) )
               .body( "[0].quantity", is( 2 ) );

        given().when().get( "api/cart/removeItem/1000" ).then().statusCode( NO_CONTENT.getStatusCode() );
        given().when()
               .get( "api/cart/items" )
               .then()
               .statusCode( OK.getStatusCode() )
               .body( "[0]", new DebugMatcher<>() )
               .body( "size()", is( 1 ) )
               .body( "[0].item.id", is( 1001 ) )
               .body( "[0].quantity", is( 1 ) );

    }

}
