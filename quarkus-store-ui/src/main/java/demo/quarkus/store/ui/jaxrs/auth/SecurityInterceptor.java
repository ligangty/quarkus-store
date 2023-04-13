/**
 * Copyright (C) 2022 Red Hat, Inc. (https://github.com/Commonjava/indy-security)
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
package demo.quarkus.store.ui.jaxrs.auth;

import io.quarkus.oidc.IdToken;
import io.quarkus.security.identity.SecurityIdentity;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.Set;

import static javax.ws.rs.HttpMethod.GET;

@ApplicationScoped
@Provider
public class SecurityInterceptor
        implements ContainerRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Context
    UriInfo info;

    @Inject
    SecurityIdentity identity;

    @Inject
    @IdToken
    JsonWebToken idToken;

    @Override
    public void filter( ContainerRequestContext requestContext )
    {

        final String path = info.getPath();

        final String method = requestContext.getMethod();

        if ( !authenticated( method ) )
        {
            requestContext.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
        }
    }

    private boolean authenticated( final String method )
    {
        logger.debug( "Roles: {}", getRoles() );
        if ( GET.equals( method ) )
        {
            logger.debug( "No access limitation for GET method." );
            return true;
        }
        Principal user = identity.getPrincipal();
        if ( user != null && StringUtils.isNotBlank( user.getName() ) )
        {
            logger.info( "Logged in user: {}", user.getName() );
            return true;
        }
        logger.info( "User not logged in, can not access non-GET methods" );
        return false;
    }

    public Set<String> getRoles()
    {
        Set<String> roles = identity.getRoles();
        if ( roles != null && !roles.isEmpty() )
        {
            return roles;
        }

        return idToken.getGroups();
    }
}
