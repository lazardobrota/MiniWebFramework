package org.example.logger;

import org.example.annotations.Bean;
import org.example.annotations.Component;
import org.example.annotations.Service;

@Service
public class MyLogger {
    int counter = 0;
    public MyLogger() {
    }

    public void log(String message) {
        System.out.println("log: " + message + " " + ++counter);
    }
}
