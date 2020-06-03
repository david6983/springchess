package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private FriendRequestRepository friendRequests;

    @GetMapping("/")
    public String welcome(@AuthenticationPrincipal User user, final Model model) {
        model.addAttribute("user", user);
        model.addAttribute("friend_requests", friendRequests.findAllByReceiverAndIsAccepted(user, false));
        model.addAttribute("friends", friendRequests.findAllByReceiverAndIsAccepted(user, true));
        return "index";
    }
}
