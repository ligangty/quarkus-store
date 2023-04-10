/**
 * Copyright (C) 2011-2022 Red Hat, Inc. (https://github.com/Commonjava/indy-repository-service)
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
package demo.quarkus.store.ui.jaxrs.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

/**
 * This customizer is used to init the jackson object mapper with indy customized features and modules
 */
@Singleton
public class JacksonCustomizer
        implements ObjectMapperCustomizer
{
    private final Logger logger = LoggerFactory.getLogger( getClass() );

    @Inject
    Instance<Module> injectedModules;

    @Override
    public void customize( ObjectMapper mapper )
    {
        mapper.setSerializationInclusion( JsonInclude.Include.NON_EMPTY );
        mapper.configure( JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, true );
        mapper.configure( DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true );

        mapper.enable( SerializationFeature.INDENT_OUTPUT, SerializationFeature.USE_EQUALITY_FOR_OBJECT_ID );
        mapper.enable( MapperFeature.AUTO_DETECT_FIELDS );

        mapper.disable( SerializationFeature.WRITE_NULL_MAP_VALUES, SerializationFeature.WRITE_EMPTY_JSON_ARRAYS );
        mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );

        inject( mapper, injectedModules );

    }

    private void inject( ObjectMapper mapper, Iterable<Module> modules )
    {
        Set<Module> injected = new HashSet<>();

        Logger logger = LoggerFactory.getLogger( getClass() );
        if ( modules != null )
        {
            for ( final Module module : modules )
            {
                injected.add( module );
            }
        }

        for ( Module module : injected )
        {
            injectSingle( mapper, module );
        }

    }

    private void injectSingle( ObjectMapper mapper, Module module )
    {
        logger.info( "Registering object-mapper module: {}", module );

        mapper.registerModule( module );
    }

}
