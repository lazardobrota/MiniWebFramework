package org.example.logger;

import org.example.annotations.Qualifier;

@Qualifier(impl = MyLogger.class)
public interface Logger {

    void log(String message);
}
