package org.example.request;

import org.example.global.Header;
import org.example.global.HttpMethodEnum;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final HttpMethodEnum httpMethodEnum;
    private final String location;
    private final Header header;
    private final Map<String, String> parameters;

    public Request() {
        this(HttpMethodEnum.GET, "/");
    }

    public Request(HttpMethodEnum httpMethodEnum, String location) {
        this(httpMethodEnum, location, new Header(), new HashMap<>());
    }

    public Request(HttpMethodEnum httpMethodEnum, String location, Header header, Map<String, String> parameters) {
        this.httpMethodEnum = httpMethodEnum;
        this.location = location;
        this.header = header;
        this.parameters = parameters;
    }

    public void addParameter(String name, String value) {
        this.parameters.put(name, value);
    }

    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return new HashMap<String, String>(this.parameters);
    }

    public boolean isMethod(HttpMethodEnum httpMethodEnum) {
        return this.getMethod().equals(httpMethodEnum);
    }

    public HttpMethodEnum getMethod() {
        return httpMethodEnum;
    }

    public String getLocation() {
        return location;
    }

    public Header getHeader() {
        return header;
    }
}
