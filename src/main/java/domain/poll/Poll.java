package domain.poll;

import core.OmniRepoRoot;
import domain.poll.exceptions.MaximumVotersCountExceededException;
import domain.poll.exceptions.NoOptionsSetException;
import org.bson.types.ObjectId;

import java.util.*;

public class Poll implements OmniRepoRoot<ObjectId> {

    private ObjectId id;
    private String title;
    private String description;
    private Set<String> options;
    private Map<String, Set<Integer>> voteMap = new HashMap<>();
    private int maxVoters = 0;

    public static Poll create() {
        return new Poll();
    }

    protected Poll() {
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void setOptions(Set<String> options) {
        if (options.size() < 1) {
            throw new IllegalArgumentException("Poll must have at least one option.");
        }
        this.options = options;
    }

    public void addVote(String name, Set<Integer> selectedOptions) throws NoOptionsSetException, MaximumVotersCountExceededException {
        if(options == null || options.size() == 0) {
            throw new NoOptionsSetException();
        }
        if (maxVoters > 0 && voteMap.keySet().size() < maxVoters) {
            throw new MaximumVotersCountExceededException("Maximum voters count (" + maxVoters + ") exceeded.");
        }
        voteMap.put(name, selectedOptions);
    }

    public int countVoters() {
        return voteMap.size();
    }

    public Set<Integer> getVoteFrom(String voterName) {
        return voteMap.get(voterName);
    }

    public int[] countVotesPerOption() {
        int [] optionCount = new int[options.size()];
        Arrays.fill(optionCount, 0);

        for (String votant : voteMap.keySet()) {
            Set<Integer> votes = voteMap.get(votant);
            for (int vote : votes) {
                optionCount[vote] ++;
            }
        }

        return optionCount;
    }

    public void setMaxVoters(int max) {
        maxVoters = max;
    }

    public int getMaxVoters() {
        return maxVoters;
    }

    public void addOption(String optionTitle) {
        options.add(optionTitle);
    }

    public Map<String, Set<Integer>> getVoteMap() {
        return voteMap;
    }

    public void setVoteMap(Map<String, Set<Integer>> voteMap) {
        this.voteMap = voteMap;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", options=" + options +
                ", maxVoters=" + maxVoters +
                '}';
    }
}
