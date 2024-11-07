package org.example.service;

import org.example.annotations.Autowired;
import org.example.annotations.Service;

@Service
public class BreadService {

    @Autowired
    private UserService userService;

    public String site() {
        StringBuilder htmlBody = new StringBuilder("<body>" +
                "<form method=\"POST\" action=\"/bread\">" +
                "   <label>Bread</label>" +
                "   <input name=\"bread\" type=\"text\"> <br><br>" +
                "   <button>Save Quote</button>" +
                "</form>"
        );
        htmlBody.append(" </body>");

        String content =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "  <head>" +
                        "<link rel=\"icon\" href=\"data:;base64,=\">" + //satisfy annoying \favicon call
                        "  </head>" +
                        htmlBody.toString() +
                        "</html>";

        String response =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n\r\n";
        response += content;

        return response;
    }

    public String addBread(String bread) {
        StringBuilder htmlBody = new StringBuilder("<body>" +
                "<p>I got ").append(bread).append("</p>"
        );
        htmlBody.append(" </body>");

        String content =
                "<!DOCTYPE html>" +
                        "<html>" +
                        "  <head>" +
                        "<link rel=\"icon\" href=\"data:;base64,=\">" + //satisfy annoying \favicon call
                        "  </head>" +
                        htmlBody.toString() +
                        "</html>";

        String response =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html\r\n\r\n";
        response += content;

        return response;
    }
}
