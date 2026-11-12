package com.example.demo.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hello")
public class HelloController {
    @GetMapping("helloWorld")
    public String helloWord(){
        return "hello World!";
    }
}
