package com.fr.yncrea.isen.cir3.chess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    @GetMapping("/")
    public String welcome(final Model model) {

        return "index";
    }
}
