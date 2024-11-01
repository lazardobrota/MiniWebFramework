package org.example.request;

import org.example.global.Header;
import org.example.global.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private final HttpMethod httpMethod;
    private final String location;
    private final Header header;
    private final Map<String, String> parameters;

    public Request() {
        this(HttpMethod.GET, "/");
    }

    public Request(HttpMethod httpMethod, String location) {
        this(httpMethod, location, new Header(), new HashMap<>());
    }

    public Request(HttpMethod httpMethod, String location, Header header, Map<String, String> parameters) {
        this.httpMethod = httpMethod;
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

    public boolean isMethod(HttpMethod httpMethod) {
        return this.getMethod().equals(httpMethod);
    }

    public HttpMethod getMethod() {
        return httpMethod;
    }

    public String getLocation() {
        return location;
    }

    public Header getHeader() {
        return header;
    }
}
