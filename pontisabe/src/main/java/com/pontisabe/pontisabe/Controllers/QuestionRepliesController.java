package com.pontisabe.pontisabe.Controllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pontisabe.pontisabe.Entities.Answer;
import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Entities.Question;
import com.pontisabe.pontisabe.Entities.Reply;
import com.pontisabe.pontisabe.Services.AnswerService;
import com.pontisabe.pontisabe.Services.ForumService;
import com.pontisabe.pontisabe.Services.QuestionService;
import com.pontisabe.pontisabe.Services.ReplyService;

@Controller
public class QuestionRepliesController {

    private final QuestionService questionService = new QuestionService();
    private final ForumService forumService = new ForumService();
    private final AnswerService answerService = new AnswerService();
    private final ReplyService replyService = new ReplyService();

    private Long idQuestion;

    @GetMapping("/questionRepliesPage")
    public String showQuestionRepliesPage(@RequestParam("id") Long id,
                                        @RequestParam("userId") Long userId,  
                                        Model model) {
        // Obtener el foro usando el id
        Forum forum = forumService.getForumById(id);
        idQuestion = id;  // Setting the question ID here

        if (forum != null && forum.getQuestion() != null) {
            Question question = questionService.getQuestionById(forum.getQuestion().getId());
            List<Answer> answers = answerService.getAnswersByQuestionId(question.getId());

            // Crear un mapa para almacenar los replies de cada answer
            Map<Long, List<Reply>> repliesMap = new HashMap<>();
            for (Answer answer : answers) {
                List<Reply> replies = replyService.getRepliesByAnswerId(answer.getId());
                repliesMap.put(answer.getId(), replies);
            }

            model.addAttribute("forum", forum);
            model.addAttribute("question", question);
            model.addAttribute("answers", answers);
            model.addAttribute("repliesMap", repliesMap);
            model.addAttribute("userId", userId); 
            model.addAttribute("idQuestion", idQuestion); // Pass the idQuestion to the model
        } else {
            model.addAttribute("error", "Question not found");
        }

        return "questionRepliesPage";
    }



    @PostMapping("/addAnswer")
    public String addAnswer(@RequestParam("answerText") String answerText,
                            @RequestParam(value = "anonym", defaultValue = "false") boolean anonym,
                            @RequestParam("forumId") Long forumId,
                            @RequestParam("userId") Long userId) { 
        // Insert the answer
        boolean success = answerService.insertAnswer(answerText, anonym, userId, idQuestion);

        if (success) {
            // Redirect to the same page with updated question and answers
            return "redirect:/questionRepliesPage?id=" + forumId + "&userId=" + userId;
        } else {
            return "redirect:/errorPage";
        }
    }


    @PostMapping("/addReply")
    public String addReply(@RequestParam("replyText") String replyText,
                        @RequestParam(value = "anonym", defaultValue = "false") boolean anonym,
                        @RequestParam("answerId") Long answerId,
                        @RequestParam("forumId") Long forumId,
                        @RequestParam("idQuestion") Long idQuestion,
                        @RequestParam("userId") Long userId) {

        // Verifica si idQuestion no es nulo
        System.out.println("Received idQuestion: " + idQuestion);

        // Insertar la réplica
        boolean success = replyService.insertReply(replyText, anonym, userId, answerId);

        if (success) {
            // Redirigir para recargar la página con la pregunta y respuestas actualizadas
            return "redirect:/questionRepliesPage?id=" + forumId + "&userId=" + userId;
        } else {
            return "redirect:/errorPage";  // Asegúrate de que tienes una ruta de error válida
        }
    }

}
