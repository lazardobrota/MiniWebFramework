package org.example.global;

import org.example.annotations.Controller;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static Map<String, String> getParamsFromRoute(String route) {
        String[] splittedRoute = route.split("\\?");
        if(splittedRoute.length == 1) {
            return new HashMap<String, String>();
        }

        return Helper.getParamsFromQuery(splittedRoute[1]);
    }

    public static Map<String, String> getParamsFromQuery(String paramsString) {
        Map<String, String> result = new HashMap<>();

        String[] pairs = paramsString.split("&");
        for (String pair : pairs) {
            String[] keyPair = pair.split("=");
            result.put(keyPair[0], keyPair[1]);
        }

        return result;
    }
}
