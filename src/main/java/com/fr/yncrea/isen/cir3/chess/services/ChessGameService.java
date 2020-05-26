package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.domain.Figure;
import com.fr.yncrea.isen.cir3.chess.domain.FigureName;
import com.fr.yncrea.isen.cir3.chess.domain.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * factory code of the game.
 */
@Service
public class ChessGameService {
    /**
     * logger.
     */
    private Logger logger = LoggerFactory.getLogger(ChessGameService.class);

    private void addFigureToGrid(
            List<Figure> grid,
            Game game,
            int x,
            int y,
            int code,
            String name,
            int owner
    ) {
        Figure fig = new Figure();

        fig.setCode(code);
        fig.setX(x);
        fig.setY(y);
        fig.setKilled(0);
        fig.setName(name);
        fig.setOwner(owner);
        fig.setGame(game);

        grid.add(fig);
    }

    /**
     * generate a grid of chess with all the pawns
     * @param game game that contains the grid
     */
    public void generateGrid(final Game game) {
        // create a list of Figures
        List<Figure> grid = new ArrayList<>();

        for (int i = 0; i < Game.NUMBER_OF_PLAYER_IN_GAME; i++) {
            for (int j = 0; j < Game.WIDTH; j++) {
                String figName = Game.FIGURES_PLACEMENT.get(j);

                addFigureToGrid(grid, game, j, 5 * (1 - i) + 1, FigureName.PAWN.ordinal(),"pawn", i);
                addFigureToGrid(grid, game, j, 7 * (1 - i), FigureName.valueOf(figName.toUpperCase()).ordinal(), figName, i);
            }
        }
        // add the grid to the game
        game.setGrid(grid);
        logger.info("grid successfully generated");
    }
}
