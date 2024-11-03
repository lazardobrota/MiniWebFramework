package org.example;

import org.example.annotations.Controller;
import org.example.reflection.HttpController;
import org.example.server.Server;

import java.io.IOException;
import java.util.Set;

public class SprungApplication {

    private final DIEngine diEngine;
    private final HttpController httpController;

    public SprungApplication() {
        diEngine = new DIEngine();
        httpController = new HttpController();
    }

    public void run() {
        try {
            Set<Class<?>> controllers = Helper.getAllClassesWithAnnotation(Controller.class);
            for (Class<?> controllerClazz : controllers) {
                createAndGenerateUrls(controllerClazz);
            }

            Server.run(httpController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> void createAndGenerateUrls(Class<T> controllerClazz) {
        T obj = diEngine.inject(controllerClazz);
        httpController.generateUrls(controllerClazz, obj);
    }
}
