package org.example.logger;

import org.example.annotations.Component;
import org.example.annotations.Service;

@Component
public class MyLogger2 implements Logger{
    int counter = -1;
    @Override
    public void log(String message) {
        System.out.println("AAAAAA " + message + counter--);
    }
}
