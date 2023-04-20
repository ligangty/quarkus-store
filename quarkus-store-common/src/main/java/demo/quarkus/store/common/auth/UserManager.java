package demo.quarkus.store.common.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.oidc.IdToken;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ApplicationScoped
public class UserManager
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    public static final String ATTR_USER_NAME = "user_name";

    public static final String ATTR_USER_FIRST_NAME = "user_first_name";

    public static final String ATTR_USER_LAST_NAME = "user_last_name";

    public static final String ATTR_EMAIL = "user_email";

    @Inject
    SecurityIdentity identity;

    @Inject
    @IdToken
    JsonWebToken idToken;

    @Inject
    ObjectMapper mapper;

    public Set<UserRole> getRoles()
    {
        Set<String> roles = identity.getRoles();
        if ( roles == null || roles.isEmpty() )
        {
            //TODO: This needs to be sure if make sense
            roles = idToken.getGroups();
        }
        if ( roles != null && !roles.isEmpty() )
        {
            logger.debug( "roles: {}", roles );
            return roles.stream().map( UserRole::fromString ).collect( Collectors.toSet() );
        }

        logger.debug( "Converted Roles: {}", roles );

        return Collections.emptySet();
    }

    public Map<String, String> getUserAttributes()
    {
        Map<String, String> userAttributes = new HashMap<>();
        Principal user = identity.getPrincipal();
        if ( user != null && isNotBlank( user.getName() ) )
        {
            userAttributes.put( ATTR_USER_NAME, user.getName() );
        }
        Map<String, Object> attributes = identity.getAttributes();
        UserInfo userInfo = (UserInfo) attributes.get( "userinfo" );
        if ( userInfo != null && userInfo.getUserInfoString() != null )
        {
            try
            {
                Map<String, Object> userInfoMap = mapper.readValue( userInfo.getUserInfoString(), Map.class );
                logger.debug( "userInfo Map: {}", userInfoMap );
                final String firstName = (String) userInfoMap.get( "given_name" );
                if ( isNotBlank( firstName ) )
                {
                    userAttributes.put( ATTR_USER_FIRST_NAME, firstName );
                }
                final String lastName = (String) userInfoMap.get( "family_name" );
                if ( isNotBlank( lastName ) )
                {
                    userAttributes.put( ATTR_USER_LAST_NAME, lastName );
                }
                final String email = (String) userInfoMap.get( "email" );
                if ( isNotBlank( email ) )
                {
                    userAttributes.put( ATTR_EMAIL, email );
                }
            }
            catch ( JsonProcessingException e )
            {
                logger.error( "Cannot parse user info", e );
            }

        }

        return userAttributes;
    }

}
