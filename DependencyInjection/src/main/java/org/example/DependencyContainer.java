package org.example;

import java.util.HashMap;
import java.util.Map;

public class DependencyContainer {

    private Map<Class<?>, Class<?>> injections;

    public DependencyContainer() {
        this.injections = new HashMap<>();
    }

    public void configure() {
        //addInjection(Logger.class, MyLogger.class);
    }

    private <T> void addInjection(Class<T> clazz, Class<? extends T> clazzImplementation) {
        injections.put(clazz, clazzImplementation.asSubclass(clazz));
    }

    public <T> Class<? extends T> getInjection(Class<T> type) {
        Class<?> injection = injections.get(type);

        if (injection == null)
            throw new RuntimeException("No Dependency Injection for type: " + type);

        return injection.asSubclass(type);
    }
}
