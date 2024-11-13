package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Entities.User;
import com.pontisabe.pontisabe.Services.ForumService;
import com.pontisabe.pontisabe.Services.QuestionService;

@Controller
public class MainPageController {

    private final ForumService forumService= new ForumService();
    private final QuestionService questionService= new QuestionService();

    @GetMapping("/mainPage")
    public String showMainPage(@RequestParam("userId") Long userId, Model model) {
    List<Forum> forums = forumService.getAllForums();

    if (forums != null) {
        for (Forum forum : forums) {
            Question question = forum.getQuestion();
            if (!question.isAnonym()) {
                User user = questionService.findUserById(question.getUser().getId());
                question.setUser(user);
            }
        }
    } else {
        forums = new ArrayList<>();
    }

    model.addAttribute("forums", forums);
    model.addAttribute("userId", userId);
    return "mainPage";
}
}