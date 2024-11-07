package org.example.reflection;

import java.lang.reflect.Method;

public class HttpMethod {
    private Object controller;
    private Method method;

    public HttpMethod(Object controller, Method method) {
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
