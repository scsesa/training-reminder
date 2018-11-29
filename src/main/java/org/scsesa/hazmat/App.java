package org.scsesa.hazmat;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.*;

/**
 * Created by jcreasy on 11/28/18.
 */
@ApplicationPath("/")
public class App extends ResourceConfig {
    public App() {
        property(ServerProperties.TRACING, "ALL");
        property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
    }
}
