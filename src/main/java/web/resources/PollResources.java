package web.resources;

import core.OmniRepo;
import domain.poll.Poll;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import web.AppServer;

import java.util.List;

public class PollResources extends ServerResource {

    @Override
    public void doInit() {
       omniRepo = AppServer.getRepo();
    }

    @Get("json")
    public Representation get(){
        List<Poll> polls = omniRepo.getAll(Poll.class);
        return new JacksonRepresentation<List<Poll>>(polls);
    }

    private OmniRepo omniRepo;
}
