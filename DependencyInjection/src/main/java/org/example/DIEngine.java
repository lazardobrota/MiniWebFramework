package org.example;

import org.example.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

                Class<?> fieldType = getInjectableIfQuailifer(field.getType());

                if (isSingleton(fieldType)) {
                    field.set(instance, getSingleton(fieldType));
                    log(InjectionType.SINGLETON, field, false);
                }
                else if (isComponent(fieldType)) {
                    field.set(instance, inject(fieldType));
                    log(InjectionType.PROTOTYPE, field, true);
                }
                else
                    throw new InstantiationException("No Dependency Injection for type: " + fieldType);
            }
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Class<?> getInjectableIfQuailifer(Class<?> type) {
        Class<?> implType = type;
        if (isQualifier(type))
            implType = getInjectableIfQuailifer(this.dependencyContainer.getInjection(type));

        return implType;
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

    private void log(InjectionType injectionType, Field field, boolean isCreated) {
        if (!field.getAnnotation(Autowired.class).verbose())
            return;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("info: ");

        if(isCreated)
            stringBuilder.append("Creating ");
        else
            stringBuilder.append("Using ");

        if (injectionType == InjectionType.SINGLETON)
            stringBuilder.append("Singleton ");
        else
            stringBuilder.append("Component ");

        stringBuilder.append("of type ").append(field.getType());
        System.out.println(stringBuilder);
    }
}
