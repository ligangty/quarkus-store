package demo.quarkus.store.jaxrs.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.quarkus.store.common.jackson.QstoreJacksonCustomizer;
import io.quarkus.jackson.ObjectMapperCustomizer;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * This customizer is used to init the jackson object mapper with indy customized features and modules
 */
@Singleton
public class JacksonCustomizer
        implements ObjectMapperCustomizer
{
    @Inject
    Instance<Module> injectedModules;

    @Override
    public void customize( ObjectMapper mapper )
    {
        QstoreJacksonCustomizer.customize( mapper, injectedModules );
    }

}
