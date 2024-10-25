package com.pontisabe.pontisabe.Controllers;

import com.pontisabe.pontisabe.Services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginPageController {

    private final AccountService accountService;

    // Constructor con inyección de dependencias
    public LoginPageController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Redirigir a la página de login por defecto
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    // Mostrar la página de login
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String param) {
        return "loginPage";  
    }

    @PostMapping("/login")
public String login(@RequestParam String username, 
                    @RequestParam String password, 
                    RedirectAttributes redirectAttributes) {
    boolean isLoggedIn = accountService.login(username, password);
    if (isLoggedIn) {
        return "redirect:/mainPage";
    } else {
        redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
        return "redirect:/login";  
    }
}
}
