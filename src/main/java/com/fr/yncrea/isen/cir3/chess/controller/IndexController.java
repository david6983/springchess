package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.GameRequest;
import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.repository.FriendRequestRepository;
import com.fr.yncrea.isen.cir3.chess.repository.GameRequestRepository;
import com.fr.yncrea.isen.cir3.chess.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private FriendRequestRepository friendRequests;

    @Autowired
    private FriendRequestService friendService;

    @Autowired
    private GameRequestRepository gameRequests;

    @GetMapping("/")
    public String welcome(@AuthenticationPrincipal User user, final Model model) {
        List<User> friends = friendService.getFriendUserList(user);

        model.addAttribute("user", user);
        model.addAttribute("friend_requests", friendRequests.findAllByReceiverAndIsAccepted(user, false));
        model.addAttribute("friends", friends);
        model.addAttribute("game_requests", gameRequests.findAllByReceiverAndIsAccepted(user, false));
        model.addAttribute("pending_game_requests", gameRequests.findAllBySenderAndIsAccepted(user, false));
        return "index";
    }
}
