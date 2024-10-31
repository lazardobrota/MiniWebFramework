package org.example.response;

import org.example.global.Header;

public abstract class Response {
    protected Header header;

    public Response() {
        this.header = new Header();
    }

    public abstract String render();
}
