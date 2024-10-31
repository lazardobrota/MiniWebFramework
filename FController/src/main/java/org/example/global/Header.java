package org.example.global;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Header {

    private Map<String, String> headers;

    public Header() {
        headers = new HashMap<>();
    }

    public void add(String key, String value) {
        this.headers.put(key, value);
    }

    public String get(String key) {
        return this.headers.get(key);
    }

    public Set<String> getKeys() {
        return this.headers.keySet();
    }

    @Override
    public String toString() {
        return this.headers.toString();
    }
}
