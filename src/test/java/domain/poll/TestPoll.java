package domain.poll;

import domain.poll.exceptions.MaximumVotersCountExceededException;
import domain.poll.exceptions.NoOptionsSetException;
import org.junit.Test;
import utils.PollFactory;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestPoll {

    @Test
    public void testCanCreatePool() {
        Poll poll = Poll.create();
        assertNotNull(poll);
    }

    @Test
    public void testCanSetTitle() {
        Poll poll = Poll.create();
        poll.setTitle("My title");
        assertEquals("My title", poll.getTitle());
    }

    @Test
    public void testCanSetDescription() {
        Poll poll = Poll.create();
        poll.setDescription("My description");
        assertEquals("My description", poll.getDescription());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testCannotSetLessThanOneOption(){
        Poll poll = Poll.create();
        Set<String> options= new HashSet<>();
        poll.setOptions(options);
    }

    @Test
    public void canAddOneOption() {
        Poll poll = PollFactory.createPollWithOptions(2);
        assertEquals(2, poll.getOptions().size());
        poll.addOption("Option #2");
        assertEquals(3, poll.getOptions().size());
    }

    @Test
    public void canAddVote() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = PollFactory.createPollWithOptions(3);
        Set<Integer> selectedOptions = new HashSet<>(Arrays.asList(1,2));
        poll.addVote("fabrice", selectedOptions);
        assertEquals(1, poll.countVoters());
    }

    @Test
    public void canUpdateVote() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = PollFactory.createPollWithOptions(5);
        String votantName = "fabrice";
        Set<Integer> initialSelection = new HashSet<>(Arrays.asList(1, 2));
        Set<Integer> updatedSelection = new HashSet<>(Arrays.asList(0, 4));
        poll.addVote(votantName, initialSelection);
        poll.addVote(votantName, updatedSelection);
        assertEquals(new HashSet<>(updatedSelection), poll.getVoteFrom(votantName));
    }

    @Test(expected = NoOptionsSetException.class)
    public void cannotVoteWhenNoOptionsAreSet() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = Poll.create();
        poll.addVote("fabrice", new HashSet<>(Arrays.asList(0, 2)));
    }


    @Test
    public void canCountVotes() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = PollFactory.createPollWithOptions(4);
        poll.addVote("fabrice", new HashSet<>(Arrays.asList(0, 2)));
        poll.addVote("franck", new HashSet<>(Arrays.asList(0, 3)));
        poll.addVote("patrick", new HashSet<>(Arrays.asList(0, 3)));

        int [] optionsSelectionCount = poll.countVotesPerOption();
        assertEquals(3, optionsSelectionCount[0]);
        assertEquals(0, optionsSelectionCount[1]);
        assertEquals(1, optionsSelectionCount[2]);
        assertEquals(2, optionsSelectionCount[3]);
    }


    @Test (expected = MaximumVotersCountExceededException.class)
    public void cannotExceedMaxVotersCount() throws NoOptionsSetException, MaximumVotersCountExceededException {
        Poll poll = PollFactory.createPollWithOptions(1);
        poll.setMaxVoters(2);
        poll.addVote("fabrice", new HashSet<>(Arrays.asList(0, 2)));
        poll.addVote("franck", new HashSet<>(Arrays.asList(0, 3)));
        poll.addVote("patrick", new HashSet<>(Arrays.asList(0, 3)));
    }

    @Test (expected = IllegalArgumentException.class)
    public void maxVotersCountMustBeGreaterThanTwo() {
        Poll poll = PollFactory.createPollWithOptions(1);
        poll.setMaxVoters(1);
    }

}
