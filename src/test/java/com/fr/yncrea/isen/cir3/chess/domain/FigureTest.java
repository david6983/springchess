package com.fr.yncrea.isen.cir3.chess.domain;

import com.fr.yncrea.isen.cir3.chess.services.ChessGameService;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

public class FigureTest {
    private Game game;

    @BeforeEach
    public void before() {
        game = new Game();
    }
}
