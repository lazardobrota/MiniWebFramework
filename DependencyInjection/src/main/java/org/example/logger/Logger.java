package org.example.logger;

import org.example.annotations.Qualifier;

@Qualifier("")
public interface Logger {

    void log(String message);
}
