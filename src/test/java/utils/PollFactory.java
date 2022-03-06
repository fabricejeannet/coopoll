package utils;


import domain.poll.Poll;

import java.util.HashSet;
import java.util.Set;

public class PollFactory {

    public static Poll createPollWithOptions(int count) {
        Poll poll = Poll.create();
        poll.setTitle("My poll title");
        poll.setDescription("My poll description");
        Set<String> options = new HashSet<>();
        for (int i = 0; i < count; i++) {
            options.add("Option #" + i);
        }
        poll.setOptions(options);
        return poll;
    }


}
