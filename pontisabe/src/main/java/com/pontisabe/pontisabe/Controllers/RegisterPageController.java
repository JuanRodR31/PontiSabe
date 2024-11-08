package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pontisabe.pontisabe.Services.AccountService;

@Controller
public class RegisterPageController {
    
    private final AccountService accountService;


    public RegisterPageController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "registerPage";  
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String names,
                               @RequestParam String lastNames,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {

        try {

            String resultMessage = accountService.createUser(username, password, names, lastNames, email);
            System.out.println(resultMessage);

            redirectAttributes.addFlashAttribute("success", resultMessage);
            return "redirect:/login";  

        } catch (Exception e) {
        
            System.out.println(e.getMessage());
            e.printStackTrace();
            
            redirectAttributes.addFlashAttribute("error", e.getMessage());            
            return "redirect:/register";
        }
    }
}

