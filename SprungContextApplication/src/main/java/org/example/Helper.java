package org.example;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Helper {

    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    public static Set<Class<?>> getAllClassesWithAnnotation(Class<?> annotation) throws IOException {
        return ClassPath.from(Helper.class.getClassLoader())
                .getAllClasses()
                .stream()
                .map(Helper::loadClass)
                .filter(Objects::nonNull)
                .filter(clazz -> clazz.isAnnotationPresent(annotation.asSubclass(java.lang.annotation.Annotation.class)))
                .collect(Collectors.toSet());
    }

    private static Class<?> loadClass(ClassPath.ClassInfo clazz) {
        try {
            return classLoader.loadClass(clazz.getName());
        }
        catch (Exception | Error ignore) {
            return null;
        }
    }


}
