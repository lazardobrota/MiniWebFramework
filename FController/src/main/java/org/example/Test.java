package org.example;

import org.example.annotations.Controller;
import org.example.annotations.Get;
import org.example.annotations.Path;
import org.example.annotations.Post;

@Controller
@Path("/user")
public class Test {

    @Get
    @Path("/hello")
    private void getHello() {
        System.out.println("GET /user/hello");
    }

    @Get
    private void get() {
        System.out.println("GET /user");
    }

    @Post
    @Path("/hello")
    private void postHello() {
        System.out.println("POST /user/hello");
    }
}
