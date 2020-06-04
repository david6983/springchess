package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.domain.GameRequest;
import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.repository.FriendRequestRepository;
import com.fr.yncrea.isen.cir3.chess.repository.GameRepository;
import com.fr.yncrea.isen.cir3.chess.repository.GameRequestRepository;
import com.fr.yncrea.isen.cir3.chess.repository.UserRepository;
import com.fr.yncrea.isen.cir3.chess.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class IndexController {
    @Autowired
    private FriendRequestRepository friendRequests;

    @Autowired
    private GameRepository games;

    @Autowired
    private UserRepository users;

    @Autowired
    private FriendRequestService friendService;

    @Autowired
    private GameRequestRepository gameRequests;

    @GetMapping("/")
    public String welcome(@AuthenticationPrincipal User user, final Model model) {
        List<User> friends = friendService.getFriendUserList(user);
        List<Game> gameList = games.findByWhitePlayerOrBlackPlayer(user, user);

        User u = users.findByUsername(user.getUsername());
        u.setPlaying(false);
        u.setLogIn(true);
        users.save(u);

        model.addAttribute("user", user);
        model.addAttribute("friend_requests", friendRequests.findAllByReceiverAndIsAccepted(user, false));
        model.addAttribute("friends", friends);
        model.addAttribute("game_requests", gameRequests.findAllByReceiverAndIsAccepted(user, false));
        model.addAttribute("pending_game_requests", gameRequests.findAllBySenderAndIsAccepted(user, false));
        model.addAttribute("games", gameList);
        return "index";
    }
}
