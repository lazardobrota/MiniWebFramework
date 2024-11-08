package org.example.controller;

import org.example.annotations.*;
import org.example.model.Bread;
import org.example.service.BreadService;

@Controller
@Path("/bread")
public class BreadController {

    @Autowired
    private BreadService breadService;

    @Get
    public Bread getBread(@QueryParam("name") String name) {
        return breadService.getBread(name);
    }

    @Post
    public Bread postBread() {
        System.out.println("POST /bread");
        return breadService.getBread("My favorite bread");
    }
}
