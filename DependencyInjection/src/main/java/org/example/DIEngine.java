package org.example;

import org.example.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DIEngine {

    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final DependencyContainer dependencyContainer = new DependencyContainer();



    public <T> T inject(Class<T> clazz) {
        return injectField(clazz);
    }

    private <T> T injectField(Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            for (Field field: clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Autowired.class))
                    continue;
                field.setAccessible(true);

                Class<?> fieldType = getInjectableIfQualifier(field);

                Object fieldInstance;

                if (isSingleton(fieldType)) {
                    fieldInstance = getSingleton(fieldType);
                    field.set(instance, fieldInstance);
                }
                else if (isComponent(fieldType)) {
                    fieldInstance = inject(fieldType);
                    field.set(instance, fieldInstance);
                }
                else
                    throw new InstantiationException("No Dependency Injection for type: " + fieldType);

                log2(fieldInstance, field, clazz);
            }

            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Class<?> getInjectableIfQualifier(Field field) {
        if (!field.isAnnotationPresent(Qualifier.class))
            return field.getType();

        Qualifier qualifier = field.getAnnotation(Qualifier.class);

        Class<?> type = field.getType();
        if (!type.isAssignableFrom(qualifier.impl()))
            throw new RuntimeException("Invalid implementation for qualifier with type " + type);

        return qualifier.impl().asSubclass(type);
    }

    private Object getSingleton(Class<?> type) {

        if (!singletons.containsKey(type)) {
            singletons.put(type, inject(type));
        }

        return singletons.get(type);
    }

    private boolean isSingleton(Class<?> clazz) {
        return clazz.isAnnotationPresent(Service.class) ||
                clazz.isAnnotationPresent(Bean.class) && clazz.getAnnotation(Bean.class).scope() == InjectionType.SINGLETON;
    }

    private boolean isComponent(Class<?> clazz) {
        return clazz.isAnnotationPresent(Component.class) ||
                clazz.isAnnotationPresent(Bean.class) && clazz.getAnnotation(Bean.class).scope() == InjectionType.PROTOTYPE;
    }

    private boolean isQualifier(Class<?> clazz) {
        return clazz.isAnnotationPresent(Qualifier.class);
    }

    private void log2(Object instance, Field field, Class<?> parentClazz) {
        String str = "Initialized: " +
                field.getType().getSimpleName() + ' ' +
                field.getName() + " in " +
                parentClazz.getSimpleName() + " on " +
                LocalDateTime.now() + " with " +
                System.identityHashCode(instance);
        System.out.println(str);
    }
}
