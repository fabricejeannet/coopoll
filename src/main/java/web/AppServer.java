package web;

import core.OmniRepo;
import domain.poll.Poll;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;
import web.resources.PollResources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppServer extends Application {


    public static void main(String [] agrs) throws Exception {
        AppServer appServer = new AppServer(8182);
    }

    public AppServer(int portNumber) throws Exception {

        Component component = new Component();
        component.getServers().add(Protocol.HTTP, portNumber);

        //Router router = new Router(getContext());
        //router.attach("/polls", PollResources.class);

        component.getDefaultHost().attach("/polls", PollResources.class);

        logger.log(Level.INFO, "Starting server on port " + portNumber);
        component.start();
    }

    public static OmniRepo getRepo() {
        if (omniRepo == null) {
            omniRepo = new OmniRepo("mongodb://localhost:8080", "coopolltestdb");
            omniRepo.map(Poll.class);
        }
        return omniRepo;
    }

    private static OmniRepo omniRepo;
    private Logger logger = Logger.getLogger("AppServer");
}
