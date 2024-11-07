package org.example.controller;

import org.example.annotations.*;
import org.example.service.BreadService;

@Controller
@Path("/bread")
public class Bread {

    @Autowired
    private BreadService breadService;

    @Get
    public String getSite() {
        return breadService.site();
    }

    @Post
    public String postBread(@QueryParam("bread") String bread) {
        System.out.println("POST /bread " + bread);
        return breadService.addBread(bread);
    }
}
