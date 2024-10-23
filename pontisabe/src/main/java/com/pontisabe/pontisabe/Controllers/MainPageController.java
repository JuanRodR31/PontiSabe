package com.pontisabe.pontisabe.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MainPageController {
    @GetMapping("/mainpage")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
