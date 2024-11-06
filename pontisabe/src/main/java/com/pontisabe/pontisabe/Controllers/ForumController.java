package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Entities.User;
import com.pontisabe.pontisabe.Services.ForumService;
import com.pontisabe.pontisabe.Services.QuestionService;

@Controller
public class ForumController {

    private final ForumService forumService= new ForumService();
    private final QuestionService questionService= new QuestionService();

    @GetMapping("/mainPage")
    public String showMainPage(Model model) {
        List<Forum> forums = forumService.getAllForums();

        for (Forum forum : forums) {
            Question question = forum.getQuestion();
            if (!question.isAnonym()) {  
                User user = questionService.findUserById(question.getUser().getId());
                question.setUser(user);  
            }
        }
        model.addAttribute("forums", forums);
        return "mainPage"; 
    }
} 