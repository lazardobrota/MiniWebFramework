package org.example.controller;

import org.example.annotations.Controller;
import org.example.annotations.Get;
import org.example.annotations.Path;

@Controller
@Path("/bread")
public class Bread {

    @Get
    @Path("/all")
    public void getAllBread() {
        System.out.println("GET /bread/all");
    }
}
