package org.example.reflection;

import org.example.annotations.*;
import org.example.global.HttpMethodEnum;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class HttpController {
    private final Map<String, HttpMethod> urlMap;

    public HttpController() {
        urlMap = new HashMap<>();
    }

    public <T> void generateUrls(Class<T> controllerClazz, T object) {
        if (!controllerClazz.isAnnotationPresent(Controller.class) || !controllerClazz.isAnnotationPresent(Path.class))
            return;

        Path path = controllerClazz.getAnnotation(Path.class);


        for (Method method: controllerClazz.getDeclaredMethods()) {
            boolean isGet = method.isAnnotationPresent(Get.class);
            boolean isPost = method.isAnnotationPresent(Post.class);

            if (!isGet && !isPost)
                continue;

            if (isGet && isPost)
                throw new RuntimeException("Method can't be GET and POST at the same time in " + controllerClazz);

            StringBuilder stringBuilder = new StringBuilder();

            if (isGet)
                stringBuilder.append(HttpMethodEnum.GET).append(" ");
            else
                stringBuilder.append(HttpMethodEnum.POST).append(" ");

            stringBuilder.append(path.value());

            if (method.isAnnotationPresent(Path.class)) {
                Path methodPath = method.getAnnotation(Path.class);
                stringBuilder.append(methodPath.value());
            }

            urlMap.put(stringBuilder.toString(), new HttpMethod(object, method));
        }
    }

    public static Object cast(String value, Class<?> type) {

        if (type == Integer.class)
            return Integer.valueOf(value);
        if (type == Boolean.class)
            return Boolean.valueOf(value);
        if (type == String.class)
            return value;

        return null;
    }

    public HttpMethod getMethod(String url) {
        if (url.equals("GET /favicon.ico"))
            return null;
        if (!urlMap.containsKey(url))
            throw new RuntimeException("URL: \"" + url + "\"doesn't exist");

        return urlMap.get(url);
    }
}
