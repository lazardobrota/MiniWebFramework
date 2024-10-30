package org.example.logger;

import org.example.annotations.Autowired;

public class Test {

    @Autowired
    private MyLogger logger;

    public void log() {
        logger.log("whyy");
    }

}
