package org.example;

import org.example.annotations.Controller;
import org.example.annotations.Get;
import org.example.annotations.Path;

@Controller
@Path("/user")
public class Test {

    @Get
    @Path("/hello")
    private void get() {

    }
}
