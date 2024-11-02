package org.example.server;

import org.example.global.Header;
import org.example.global.Helper;
import org.example.global.HttpMethodEnum;
import org.example.reflection.HttpController;
import org.example.request.Request;
import org.example.response.JsonResponse;
import org.example.response.Response;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final HttpController httpController;

    public ServerThread(Socket socket, HttpController httpController) {
        this.socket = socket;
        this.httpController = httpController;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
        try {
            Request request = this.generateRequest();
            if (request == null) {
                in.close();
                out.close();
                socket.close();
                return;
            }

            //TODO Reflection
            httpController.callRest(request.getMethod() + " " + request.getLocation(), request.getParameters());


            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("route_location", request.getLocation());
            responseMap.put("route_method", request.getMethod().toString());
            responseMap.put("parameters", request.getParameters());
            Response response = new JsonResponse(responseMap);

            out.println(response.render());

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Request generateRequest() throws IOException {
        String command = in.readLine();
        if(command == null) {
            return null;
        }

        String[] actionRow = command.split(" ");
        HttpMethodEnum httpMethodEnum = HttpMethodEnum.valueOf(actionRow[0]);
        String wholeRoute = actionRow[1];
        Header header = new Header();
        Map<String, String> parameters = Helper.getParamsFromRoute(wholeRoute);

        do {
            command = in.readLine();
            String[] headerRow = command.split(": ");
            if(headerRow.length == 2) {
                header.add(headerRow[0], headerRow[1]);
            }
        } while(!command.trim().equals(""));

        if(httpMethodEnum.equals(HttpMethodEnum.POST)) {
            int contentLength = Integer.parseInt(header.get("content-length"));
            char[] buff = new char[contentLength];
            in.read(buff, 0, contentLength);
            String parametersString = new String(buff);

            Map<String, String> postParameters = Helper.getParamsFromQuery(parametersString);
            for (String parameterName : postParameters.keySet()) {
                parameters.put(parameterName, postParameters.get(parameterName));
            }
        }

        Request request = new Request(httpMethodEnum, wholeRoute.split("\\?")[0], header, parameters);

        return request;
    }
}
