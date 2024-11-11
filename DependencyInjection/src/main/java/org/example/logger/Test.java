package org.example.logger;

import org.example.annotations.Autowired;
import org.example.annotations.Qualifier;

public class Test {

    @Autowired
    @Qualifier(impl = MyLogger.class)
    private Logger logger;

    @Autowired
    @Qualifier(impl = MyLogger2.class)
    private Logger logger1;

    public void log() {
        logger1.log("whyy");
        logger.log("whyy");

    }

}
