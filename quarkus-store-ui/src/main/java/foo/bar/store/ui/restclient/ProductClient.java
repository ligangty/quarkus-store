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
package foo.bar.store.ui.restclient;

import foo.bar.store.ui.model.Product;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/products")
@RegisterRestClient(configKey="store-service-api")
public interface ProductClient
{
    @POST
    @Consumes( APPLICATION_JSON )
    Response create( Product entity );

    @DELETE
    @Path( "/{id}" )
    Response deleteById( @PathParam( "id" ) Long id );

    @GET
    @Path( "/{id}" )
    Response findById( @PathParam( "id" ) Long id );

    @GET
    List<Product> listAll( @QueryParam( "start" ) Integer startPosition, @QueryParam( "max" ) Integer maxResult );

    @PUT
    @Path( "/{id}" )
    @Consumes( APPLICATION_JSON )
    Response update( @PathParam( "id" ) final Long id, final Product entity );

    @GET
    @Path( "/byCategory" )
    @Produces( APPLICATION_JSON )
    Response findProductsByCategory( @QueryParam( "category" ) final String categoryName );
}
