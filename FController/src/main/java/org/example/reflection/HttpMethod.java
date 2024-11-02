package org.example.reflection;

import java.lang.reflect.Method;
import java.util.Map;

public class HttpMethod {
    private Object controller;
    private Method method;

    public HttpMethod(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    public void addValidParams() {

    }

    public Object getController() {
        return controller;
    }

    public Method getMethod() {
        return method;
    }
}
