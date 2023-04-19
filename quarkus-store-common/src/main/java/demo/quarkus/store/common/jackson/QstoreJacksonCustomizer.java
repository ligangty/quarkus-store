package demo.quarkus.store.common.jackson;

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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashSet;
import java.util.Set;

/**
 * This customizer is used to init the jackson object mapper with indy customized features and modules
 */
public class QstoreJacksonCustomizer
{
    private static final Logger logger = LoggerFactory.getLogger( QstoreJacksonCustomizer.class );

    public static void customize( ObjectMapper mapper, Instance<Module> injectedModules )
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

    private static void inject( ObjectMapper mapper, Iterable<Module> modules )
    {
        Set<Module> injected = new HashSet<>();

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

    private static void injectSingle( ObjectMapper mapper, Module module )
    {
        logger.info( "Registering object-mapper module: {}", module );

        mapper.registerModule( module );
    }

}
