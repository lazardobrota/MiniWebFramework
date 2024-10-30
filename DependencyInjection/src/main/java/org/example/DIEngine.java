package org.example;

import org.example.annotations.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DIEngine {

    //private final DependencyContainer2 dependencyContainer2;
    private final Map<Class<?>, Object> singletons;

    private DIEngine(DependencyContainer2 dependencyContainer2) {
        singletons = new HashMap<>();
        //this.dependencyContainer2 = dependencyContainer2;
        //this.dependencyContainer2.configure();
    }

    public static DIEngine getInjector(DependencyContainer2 dependencyContainer2) {
        return new DIEngine(dependencyContainer2);
    }

    public <T> T inject(Class<T> clazz) {
        return injectField(clazz);
    }

    //TODO Add Qualifier
    private <T> T injectField(Class<T> clazz) {
        try {
            T instance = clazz.getConstructor().newInstance();
            for (Field field: clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Autowired.class))
                    continue;
                field.setAccessible(true);

                Class<?> fieldType = field.getType();

                if (isSingleton(fieldType)) {
                    System.out.println("Singlton");
                    field.set(instance, getSingleton(fieldType));
                }
                else if (isComponent(fieldType)) {
                    System.out.println("Componenta");
                    field.set(instance, inject(fieldType));
                }
                else
                    throw new InstantiationException("No Dependency Injection for type: " + fieldType);
//                else
//                    field.set(instance, inject(this.dependencyContainer2.getInjection(field.getType())));
            }

            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Object getSingleton(Class<?> type) {
        
        if (!singletons.containsKey(type))
            singletons.put(type, inject(type));
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
}
