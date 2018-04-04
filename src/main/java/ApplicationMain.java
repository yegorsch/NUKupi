import REST.IDService;
import REST.ImageService;
import REST.ProductService;
import REST.UserService;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationPath("rest")
public class ApplicationMain extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ApplicationMain() {
        singletons.add(new ProductService());
        singletons.add(new ImageService());
        singletons.add(new UserService());
        singletons.add(new IDService());
        singletons.add(new LoggingFeature(Logger.getLogger(ApplicationMain.class.getName()), LoggingFeature.Verbosity.PAYLOAD_ANY));
    }

    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}