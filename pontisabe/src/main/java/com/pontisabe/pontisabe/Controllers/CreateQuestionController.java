package com.pontisabe.pontisabe.Controllers;

import java.sql.Date;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Services.AccountService;
import com.pontisabe.pontisabe.Services.ForumService;
import com.pontisabe.pontisabe.Services.QuestionService;


@Controller
public class CreateQuestionController {

    private final QuestionService questionService = new QuestionService();
    private final ForumService forumService = new ForumService();
    private final AccountService accountService = new AccountService();

    @GetMapping("/createQuestionPage")
    public String showCreateQuestionPage() {
        return "createQuestionPage";
    }

    @PostMapping("/addQuestion")
    public String addQuestion(
        @RequestParam String forumTitle,
        @RequestParam String questionText,
        @RequestParam(required = false) Boolean anonym) {
        System.out.println(forumTitle);
        Question question = new Question();
        question.setQuestionText(questionText);
        question.setAnonym(anonym != null && anonym);
        question.setPublishDate(Date.valueOf(LocalDate.now()));
        
        question.setUser(accountService.findUserById(1L));

        Long questionId = questionService.insertQuestionToDbAndGetId(question);
        question.setId(questionId);

        forumService.createForum(forumTitle, question);
        

        return "redirect:/mainPage";
    }
}
