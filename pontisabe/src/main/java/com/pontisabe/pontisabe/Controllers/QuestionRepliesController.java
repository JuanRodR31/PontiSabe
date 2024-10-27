package com.pontisabe.pontisabe.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pontisabe.pontisabe.Entities.Answer;
import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Services.AnswerService;
import com.pontisabe.pontisabe.Services.ForumService;
import com.pontisabe.pontisabe.Services.QuestionService;

@Controller
public class QuestionRepliesController {

    private final QuestionService questionService = new QuestionService();
    private final ForumService forumService = new ForumService();
    private final AnswerService answerService = new AnswerService();
    private Long idQuestion;
    @GetMapping("/questionRepliesPage")
    public String showQuestionRepliesPage(@RequestParam("id") Long id, Model model) {
        // Obtener el foro usando el id
        Forum forum = forumService.getForumById(id);
        idQuestion = id;
        if (forum != null && forum.getQuestion() != null) {
            Question question = questionService.getQuestionById(forum.getQuestion().getId());
            List<Answer> answers = answerService.getAnswersByQuestionId(question.getId());

            model.addAttribute("forum", forum);
            model.addAttribute("question", question);
            model.addAttribute("answers", answers);
        } else {
            model.addAttribute("error", "Question not found");
        }

        return "questionRepliesPage";
    }
    @PostMapping("/addAnswer")
    public String addAnswer(@RequestParam("answerText") String answerText,
                            @RequestParam(value = "anonym", defaultValue = "false") boolean anonym,
                            @RequestParam("forumId") Long forumId) {
        // Insertar la respuesta
        Long userId = 1L;
        boolean success = answerService.insertAnswer(answerText, anonym, userId, idQuestion);

        if (success) {
            // Redirigir para recargar la página con la pregunta y respuestas actualizadas
            return "redirect:/questionRepliesPage?id=" + forumId;
        } else {
            // Manejar el caso de error si la inserción falla
            return "redirect:/errorPage";
        }
    }
}

