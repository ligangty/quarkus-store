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
package demo.quarkus.store.util;

import demo.quarkus.store.common.auth.UserManager;
import demo.quarkus.store.model.Customer;
import demo.quarkus.store.service.CustomerService;
import demo.quarkus.store.view.LoggedIn;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ApplicationScoped
public class LoggedInCustomerProducer
{
    @Inject
    UserManager userManager;

    @Inject
    CustomerService service;

    @Produces
    @LoggedIn
    public Customer produceLoggedIn()
    {
        Customer user = new Customer();
        Map<String, String> userAttrs = userManager.getUserAttributes();
        String userName = userAttrs.get( UserManager.ATTR_USER_NAME );
        if ( isNotBlank( userName ) )
        {
            Customer detailedUser = service.findCustomer( userName );
            if ( detailedUser != null )
            {
                user = detailedUser;
            }
        }
        else
        {
            return null;
        }

        final String firstName = userAttrs.get( UserManager.ATTR_USER_FIRST_NAME );
        if ( isNotBlank( firstName ) )
        {
            user.setFirstName( firstName );
        }
        final String lastName = userAttrs.get( UserManager.ATTR_USER_LAST_NAME );
        if ( isNotBlank( lastName ) )
        {
            user.setLastName( lastName );
        }
        final String email = userAttrs.get( UserManager.ATTR_EMAIL );
        if ( isNotBlank( email ) )
        {
            user.setEmail( email );
        }
        if ( !userManager.getRoles().isEmpty() )
        {
            user.setRole( userManager.getRoles().stream().findFirst().get() );
        }

        return user;
    }
}
