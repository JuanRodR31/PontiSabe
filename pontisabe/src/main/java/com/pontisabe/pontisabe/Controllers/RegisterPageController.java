package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterPageController {
    @GetMapping("/register")
    public String showRegisterinPage(@RequestParam(required = false) String param) {
        return "registerPage";  
    }
}
