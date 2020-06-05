package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.form.UserForm;
import com.fr.yncrea.isen.cir3.chess.services.DbUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DbUserDetailsService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", userService.createForm(null));
        return "user/register";
    }

    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute("user") UserForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", form);
            return "user/register";
        }

        userService.save(form);
        logger.info("successfully created user, need to register it in database");

        return "redirect:/login";
    }
}
