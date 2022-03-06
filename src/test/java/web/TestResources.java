package web;

import core.OmniRepo;
import org.junit.Before;
import org.junit.Test;
import utils.DBUtils;

public class TestResources {

    @Before
    public void before() {
        omniRepo = DBUtils.getOmniRepoForTest();
    }

    @Test
    public void canRetrievePolls() {

    }

    private OmniRepo omniRepo;
}
