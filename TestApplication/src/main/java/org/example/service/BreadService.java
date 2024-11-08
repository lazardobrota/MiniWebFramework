package org.example.service;

import org.example.annotations.Autowired;
import org.example.annotations.Service;
import org.example.model.Bread;

import java.util.HashMap;
import java.util.Map;

@Service
public class BreadService {

    @Autowired
    private UserService userService;

    public Bread getBread(String name) {
        return new Bread(name);
    }


}
