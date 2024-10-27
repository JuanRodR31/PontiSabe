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
public class FriendsPageController {

    private final ForumService forumService= new ForumService();
    private final QuestionService questionService= new QuestionService();

    @GetMapping("/friendsPage")
    public String showMainPage(Model model) {
        // Obtener la lista de foros
        List<Forum> forums = forumService.getForumsByFollowings(forumService.getFollowingsByFollower(1L)); // IDs de amigos del usuario con ID 1

        // Para cada foro, obtener la pregunta y el usuario si no es anónimo
        for (Forum forum : forums) {
            Question question = forum.getQuestion();
            if (!question.isAnonym()) {  // Si la pregunta no es anónima
                User user = questionService.findUserById(question.getUser().getId());
                question.setUser(user);  // Asignar el usuario a la pregunta
            }
        }

        // Pasar la lista de foros al modelo
        model.addAttribute("forums", forums);
        return "mainPage";  // Nombre del archivo HTML (mainPage.html)
    }
}
