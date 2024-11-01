package org.example.reflection;

import org.example.annotations.Controller;
import org.example.annotations.Get;
import org.example.annotations.Path;
import org.example.annotations.Post;
import org.example.global.HttpMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HttpController {
    private Object controller;
    private Method method;

    public HttpController(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
