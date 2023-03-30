package foo.bar.store.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path( "/api/hello" )
public class GreetingResource
{

    @GET
    @Produces( TEXT_PLAIN )
    public String hello()
    {
        return "Hello from RESTEasy Reactive";
    }
}