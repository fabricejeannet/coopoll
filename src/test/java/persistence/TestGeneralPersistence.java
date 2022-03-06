package persistence;

import core.OmniRepo;
import domain.poll.Poll;
import utils.PollFactory;
import domain.poll.exceptions.MaximumVotersCountExceededException;
import domain.poll.exceptions.NoOptionsSetException;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import utils.DBUtils;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestGeneralPersistence {

    @Before
    public void before(){
        omniRepo = DBUtils.getOmniRepoForTest();
    }

    @Test
    public void canSaveAPoll() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = createPollWithVotes();
        ObjectId id = omniRepo.save(poll);
        assertNotNull(id);
    }

    @Test
    public void canRetrievePoll() throws NoOptionsSetException, MaximumVotersCountExceededException {
        ObjectId id = omniRepo.save(createPollWithVotes());
        Poll poll = omniRepo.get(Poll.class, id) ;
        int[] votes = poll.countVotesPerOption();
        assertEquals(2, votes[0]);
        assertEquals(2, votes[1]);
        assertEquals(3, votes[2]);
    }

    private Poll createPollWithVotes() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = PollFactory.createPollWithOptions(3);
        poll.addVote("Fabrice", new HashSet<>(Arrays.asList(0,2)));
        poll.addVote("Teddy", new HashSet<>(Arrays.asList(1, 2)));
        poll.addVote("Markus", new HashSet<>(Arrays.asList(0,1,2)));
        return poll;
    }


    private OmniRepo omniRepo;
}
