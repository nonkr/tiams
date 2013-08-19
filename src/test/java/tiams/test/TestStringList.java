package tiams.test;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestStringList {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TestStringList.class);

    @Test
    public void test() {
        List<String> s = new ArrayList<String>();
        s.add("1");
        s.add("3");
        s.add("2");
        for (String u : s) {
            logger.info(u);
        }
    }
}
