/**
 * Copyright (C) 2023 Red Hat, Inc. (https://github.com/Commonjava/indy-ui-service)
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
package demo.quarkus.store.ui.restclient;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 * This filter is used to inject user access token to rest client for SSO purpose
 */
@Provider
@Priority( Priorities.AUTHENTICATION )
public class AuthClientRequestFilter
        implements ClientRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

//    @Inject
//    @IdToken
//    JsonWebToken idToken;

    @Inject
    JsonWebToken accessToken;

//    @Inject
//    RefreshToken refreshToken;

    @Override
    public void filter( ClientRequestContext requestContext )
    {
//        if ( idToken != null )
//        {
//            Object userName = this.idToken.getClaim( "preferred_username" );
//            logger.debug( "User: {}", userName );
//        }
        if ( accessToken != null )
        {
            String token = accessToken.getRawToken();
            logger.debug( "Access Token: {}", token );
            requestContext.getHeaders().add( HttpHeaders.AUTHORIZATION, String.format( "Bearer %s", token ) );
        }
    }
}
