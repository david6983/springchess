package com.fr.yncrea.isen.cir3.chess.controller;

import com.fr.yncrea.isen.cir3.chess.domain.Figure;
import com.fr.yncrea.isen.cir3.chess.domain.FigureName;
import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.domain.Move;
import com.fr.yncrea.isen.cir3.chess.form.PromoteForm;
import com.fr.yncrea.isen.cir3.chess.repository.FigureRepository;
import com.fr.yncrea.isen.cir3.chess.repository.GameRepository;
import com.fr.yncrea.isen.cir3.chess.repository.MoveRepository;
import com.fr.yncrea.isen.cir3.chess.services.ChessGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/game")
public class GameController {
    /**
     * default game redirection.
     */
    private static final String GAME_REDIRECTION = "redirect:/game/play/";
    /**
     * default redirection.
     */
    private static final String INDEX_REDIRECTION = "redirect:/";

    @Autowired
    private ChessGameService gameService;

    @Autowired
    private GameRepository games;

    @Autowired
    private MoveRepository moves;

    @Autowired
    private FigureRepository figures;

    private Logger logger = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/start")
    public String start() {
        // clean up
        //TODO clean according to one game not delete all the table
        games.deleteAll();
        figures.deleteAll();
        moves.deleteAll();

        // create a game
        Game g = new Game();

        games.save(g);

        // generate the grid
        gameService.generateGrid(g);

        // save generated figures
        figures.saveAll(g.getGrid());
        gameService.findKing(g);

        games.save(g);
        logger.info("figures saved from game/");

        return GAME_REDIRECTION + g.getId();
    }

    @GetMapping("/play/{id}")
    public String play(final Model model,
                       @PathVariable final Long id
    ) {
        Optional<Game> game = games.findById(id);
        if (game.isPresent()) {
            model.addAttribute("game", game.get());
            model.addAttribute("error_msg", "");
            System.out.println("Bool echec " +gameService.checkEchec(game.get()));
            System.out.println("Bool mate " + gameService.checkMate(game.get()));
            return "game-play";
        }
        logger.info("game {} not found for route /play/{}", id, id);
        return INDEX_REDIRECTION;
    }

    @GetMapping("/promote/{gameId}/{promoteId}")
    public String promote(final Model model,
                          @PathVariable final Long gameId,
                          @PathVariable final Long promoteId
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
            Optional<Figure> fig = figures.findById(promoteId);
            if (fig.isPresent()) {
                model.addAttribute("game", game.get());
                model.addAttribute("error_msg", "");
                model.addAttribute("figure", fig.get());
                System.out.println("Bool echec " + gameService.checkEchec(game.get()));
                System.out.println("Bool mate " + gameService.checkMate(game.get()));
                return "game-promote";
            }
        }
        logger.info("game {} not found for route /promote/{}/{}", gameId, gameId, promoteId);
        return INDEX_REDIRECTION;
    }

    @PostMapping("/promote")
    public String promoteForm(PromoteForm form, BindingResult result) {
        if (result.hasErrors()) {
            logger.info("error promote form");
        }

        logger.info("you decided to promote {} to a {}", form.getId(), form.getName());

        Optional<Figure> figure = figures.findById(form.getId());

        if (figure.isPresent()) {
            if (Game.FIGURES_PROMOTION.contains(form.getName())) {
                figure.get().setName(form.getName());
                figure.get().setCode(FigureName.stringToFigureName(form.getName()).ordinal());
                figures.save(figure.get());
            }

            return GAME_REDIRECTION + figure.get().getGame().getId();
        }

        return "game-promote";
    }

    @GetMapping("/move/{gameId}/{pawnId}/{x}/{y}")
    public String moveOnVoidCell(final Model model,
                                 @PathVariable final Long gameId,
                                 @PathVariable final Long pawnId,
                                 @PathVariable final Integer x,
                                 @PathVariable final Integer y
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
            // change the coordinate of the moved pawn to the new position
            Figure f = figures.getOne(pawnId);

            // the player is able to move is own pawns only
            if (f.getOwner() == game.get().getCurrentPlayer()) {
                // check the movement
                if (gameService.checkAny(game.get(), f, x, y)) {
                    f.setX(x);
                    f.setY(y);
                    f.updateCountPlayed();

                    figures.save(f);
                    logger.info("figure moved");

                    // save the move
                    Move m = new Move();
                    m.setCode(f.getMoveCode());
                    m.setPlayer(game.get().getCurrentPlayer());

                    moves.save(m);

                    // change player
                    Game g = game.get();
                    g.changePlayer();
                    games.save(g);

                    // pawn promotion
                    if (gameService.enablePromotePawn(f)) {
                        return "redirect:/game/promote/" + game.get().getId() + "/" + f.getId();
                    }
                }
            } else {
                //TODO throw exception and inform the view
                logger.info("You can't move a pawn that doesn't belong to you !");
            }

            model.addAttribute("game", game.get());
            return GAME_REDIRECTION + game.get().getId();
        }
        logger.info("game {} not found for route /move/{}/...", gameId, gameId);
        return INDEX_REDIRECTION;
    }

    @GetMapping("/move/{gameId}/{pawnId1}/{pawnId2}")
    public String moveOnAnyPawn(final Model model,
                                @PathVariable final Long gameId,
                                @PathVariable final Long pawnId1,
                                @PathVariable final Long pawnId2
    ) {
        Optional<Game> game = games.findById(gameId);
        if (game.isPresent()) {
            // change the coordinate of the moved pawn to the new position
            Figure f1 = figures.getOne(pawnId1);
            Figure f2 = figures.getOne(pawnId2);

            // the player is able to move is own pawns only
            if (f1.getOwner() == game.get().getCurrentPlayer() && f1.getOwner() != f2.getOwner()) {
                // check the movement
                if (gameService.checkAny(game.get(), f1, f2.getX(), f2.getY())) {
                    f1.setX(f2.getX());
                    f1.setY(f2.getY());
                    f1.updateCountPlayed();

                    figures.save(f1);
                    logger.info("figure moved");

                    figures.delete(f2);
                    logger.info("figure f2 deleted");

                    // save the move
                    Move m = new Move();
                    m.setCode(f1.getMoveCode());
                    m.setPlayer(game.get().getCurrentPlayer());

                    moves.save(m);

                    // change player
                    Game g = game.get();
                    g.changePlayer();

                    // delete figure f2
                    g.getGrid().remove(f2);

                    games.save(g);

                    // pawn promotion
                    if (gameService.enablePromotePawn(f1)) {
                        return "redirect:/game/promote/" + game.get().getId() + "/" + f1.getId();
                    }
                }
            } else {
                //TODO throw exception and inform the view
                logger.info("You can't move a pawn that doesn't belong to you !");
            }

            model.addAttribute("game", game.get());
            return GAME_REDIRECTION + game.get().getId();
        }
        logger.info("game {} not found for route moveOnAnyPawn", gameId);
        return INDEX_REDIRECTION;
    }

}
