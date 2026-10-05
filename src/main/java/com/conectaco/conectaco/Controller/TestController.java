package com.conectaco.conectaco.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")

public class TestController {
    @GetMapping("/hola")
    public String test(){
        return "hola desde springboot";
    }
}