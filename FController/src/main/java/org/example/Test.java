package org.example;

import org.example.annotations.*;

@Controller
@Path("/user")
public class Test {

    @Get
    @Path("/hello")
    private void getHello() {
        System.out.println("GET /user/hello");
    }

    @Get
    private void get(@QueryParam("name") String name) {
        System.out.println("GET /user : " + name);
    }

    @Post
    @Path("/hello")
    private void postHello() {
        System.out.println("POST /user/hello");
    }
}
