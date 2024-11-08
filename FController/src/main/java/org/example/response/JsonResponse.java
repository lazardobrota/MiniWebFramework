package org.example.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse extends Response{

    private final ObjectMapper mapper = new ObjectMapper();
    private Object jsonObject;

    public JsonResponse(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public String render() {
        StringBuilder responseContent = new StringBuilder();

        responseContent.append("HTTP/1.1 200 OK\n");
        for (String key : this.header.getKeys()) {
            responseContent.append(key).append(":").append(this.header.get(key)).append("\n");
        }
        responseContent.append("\n");

        responseContent.append(toJson(this.jsonObject));

        return responseContent.toString();
    }

    public String toJson(Object jsonObject) {
        try {
            return mapper.writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
