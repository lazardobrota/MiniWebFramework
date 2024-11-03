package org.example;

import org.example.annotations.Qualifier;

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
        if (!injections.containsKey(type)) {
            createInjection(type);
        }
        Class<?> injection = injections.get(type);

        if (injection == null)
            throw new RuntimeException("No Dependency Injection for type: " + type);

        return injection.asSubclass(type);
    }

    private <T> void createInjection(Class<T> type) {
        if (!type.isAnnotationPresent(Qualifier.class))
            return;

        Qualifier qualifier = type.getAnnotation(Qualifier.class);

        if (!type.isAssignableFrom(qualifier.impl()))
            throw new RuntimeException("Invalid implementation for qualifier with type " + type);

        addInjection(type, qualifier.impl().asSubclass(type));
    }
}
