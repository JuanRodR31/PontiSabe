package com.pontisabe.pontisabe.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import com.pontisabe.pontisabe.Entities.Forum;
import com.pontisabe.pontisabe.Services.ForumService;

@Controller
public class FriendsPageController {

    private final ForumService forumService= new ForumService();

    @GetMapping("/friendsPage")
public String showFriendsPage(Model model) {
    List<Long> friendIds = forumService.getFollowersByFollowing(1L);
    System.out.println("Friend IDs: " + friendIds);

    List<Forum> forums = forumService.getForumsByFollowings(friendIds);

    // Imprime el tamaño de `forums` para depurar
    System.out.println("Forums for friends: " + forums.size());

    model.addAttribute("forums", forums);
    return "mainPage";
    }
}
