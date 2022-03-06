package utils;

import core.OmniRepo;
import domain.poll.Poll;

public class DBUtils {

    public static OmniRepo getOmniRepoForTest() {
        if (omniRepo == null) {
            omniRepo = new OmniRepo("mongodb://localhost:8080", "coopolltestdb");
        }
        omniRepo.getMongoDatabase().drop();
        omniRepo.map(Poll.class);
        return omniRepo;
    }

    private static OmniRepo omniRepo;

}
