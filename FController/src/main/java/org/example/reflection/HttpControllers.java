package org.example.reflection;

import org.example.annotations.Controller;
import org.example.annotations.Get;
import org.example.annotations.Path;
import org.example.annotations.Post;
import org.example.global.HttpMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpControllers {
    private final Map<String, HttpController> urlMap;

    public HttpControllers() {
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
                throw new RuntimeException("Method can't be GET and POST at the same time");

            StringBuilder stringBuilder = new StringBuilder();

            if (isGet)
                stringBuilder.append(HttpMethod.GET).append(" ");
            else
                stringBuilder.append(HttpMethod.POST).append(" ");

            stringBuilder.append(path.value());

            if (method.isAnnotationPresent(Path.class)) {
                Path methodPath = method.getAnnotation(Path.class);
                stringBuilder.append(methodPath.value());
            }

            urlMap.put(stringBuilder.toString(), new HttpController(object,  method));
        }
    }

    public void callRest(String url) {
        HttpController httpController = urlMap.get(url);

        try {
            Method method =  httpController.getMethod();
            method.setAccessible(true);
            method.invoke(httpController.getController());
            method.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
