package org.example;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Helper {

    public static Set<Class<?>> getAllClassesWithAnnotation(Class<?> annotation) throws IOException {
        return ClassPath.from(Helper.class.getClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> !classInfo.getName().contains("module-info"))
                .map(ClassPath.ClassInfo::load)
                .filter(clazz -> clazz.isAnnotationPresent(annotation.asSubclass(java.lang.annotation.Annotation.class)))
                .collect(Collectors.toSet());
    }
}
