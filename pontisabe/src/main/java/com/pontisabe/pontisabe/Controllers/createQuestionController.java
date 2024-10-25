package com.pontisabe.pontisabe.Controllers;

import java.sql.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Services.QuestionService;


@Controller
public class createQuestionController {
    @GetMapping("/createQuestionPage")
    public String showRegisterinPage(@RequestParam(required = false) String param) {
        return "createQuestionPage";
    }
    @PostMapping("/addQuestion")
    public String addQuestion(
    @RequestParam String questionText,
    @RequestParam(required = false) Boolean anonym,
    @RequestParam String publishDate,
    @RequestParam Long userId) {
    Question question = new Question();
    QuestionService questionService = new QuestionService();
    question.setQuestionText(questionText);
    question.setAnonym(anonym != null && anonym);
    question.setPublishDate(Date.valueOf(publishDate));
    question.setUser(questionService.findUserById(Long.valueOf(userId)));

    questionService.createQuestion(question);
    return "redirect:/mainPage";  // Redirige a la página principal o donde prefieras
}
}
