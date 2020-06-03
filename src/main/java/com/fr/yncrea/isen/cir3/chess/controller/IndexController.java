package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.form.UserForm;
import com.fr.yncrea.isen.cir3.chess.repository.UserRepository;
import com.fr.yncrea.isen.cir3.chess.services.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class IndexController {

    @Autowired
    private UserRepository users;

    @GetMapping("/")
    public String welcome(final Model model) {
        return "index";
    }

    @PostMapping("/addFriend")
    public String addFriend(@ModelAttribute UserForm form) {
        User u = new User();

        if(form.getId()!=null) {
            u = users.findById(form.getId()).get();
        }
        u.setUsername(form.getUsername());
        users.save(u);
        return "add_friend";
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable Long id) {
        users.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/startGame")
    public String startGame() {
        return "game-play";
    }
/*
    @GetMapping("/all")
    public String showAllUser(Model model, UserForm form, User user) {
        model.addAttribute("usernames", DbUserDetailsService.findAll());
        return "redirect:/";
    } */
}
