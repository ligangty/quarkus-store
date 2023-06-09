package demo.quarkus.store.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.util.Properties;

public class ConfigPropertyProducer
{

    private static Properties props;

    static
    {
        props = new Properties();
        try
        {
            props.load( ConfigPropertyProducer.class.getResourceAsStream( "/config.properties" ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    @Produces
    @ConfigProperty
    public static String produceConfigProperty( InjectionPoint ip )
    {
        String key = ip.getAnnotated().getAnnotation( ConfigProperty.class ).value();

        return props.getProperty( key );
    }
}
