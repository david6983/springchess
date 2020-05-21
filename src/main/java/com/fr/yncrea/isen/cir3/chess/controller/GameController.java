package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.repository.FigureRepository;
import com.fr.yncrea.isen.cir3.chess.repository.GameRepository;
import com.fr.yncrea.isen.cir3.chess.services.ChessGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private ChessGameService gameService;

    @Autowired
    private GameRepository games;

    @Autowired
    private FigureRepository figures;

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/")
    public String display(final Model model) {
        // clean up
        games.deleteAll();
        figures.deleteAll();

        // create a game
        Game g = new Game();

        games.save(g);

        // generate the grid
        gameService.generateGrid(g);

        // save generated figures
        figures.saveAll(g.getGrid());
        logger.info("figures saved from game/");

        model.addAttribute("game", g);

        return "game-start";
    }
}
