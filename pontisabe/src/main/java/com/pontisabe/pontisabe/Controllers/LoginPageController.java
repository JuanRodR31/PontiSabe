package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginPageController {
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String param) {
        return "loginPage";  
    }
}