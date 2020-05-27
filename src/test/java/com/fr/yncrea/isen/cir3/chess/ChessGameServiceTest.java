package com.fr.yncrea.isen.cir3.chess;

import com.fr.yncrea.isen.cir3.chess.domain.Figure;
import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.services.ChessGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessGameServiceTest {
    private ChessGameService service;

    private Game game;

    @BeforeEach
    public void before() {
        service = new ChessGameService();
        game = new Game();
        service.generateGrid(game);
    }

    @Test
    public void isPositiveTest() {
        assertThat(service.isPositive(3)).isEqualTo(1);
        assertThat(service.isPositive(-23)).isEqualTo(-1);
        assertThat(service.isPositive(0)).isEqualTo(0);
    }

    @Test
    public void isSegmentFreeTest() {
        assertThat(service.isSegmentFree(game, 0, 2, 7, 2)).isTrue();
        assertThat(service.isSegmentFree(game, 2, 2, 5, 5)).isTrue();
        assertThat(service.isSegmentFree(game, 0, 7, 0, 2)).isFalse();
        assertThat(service.isSegmentFree(game, 2, 7, 7, 2)).isFalse();
    }

    @Test
    public void checkBishopTest() {
        // first diagonal
        assertThat(service.checkBishop(game, 2, 7, 0, 5)).isFalse();
        // move the pawn that is on the path of the bishop
        game.getFigureAt(1, 6).setY(5);
        assertThat(service.checkBishop(game, 2, 7, 0, 5)).isTrue();

        // second diagonal
        assertThat(service.checkBishop(game, 2, 7, 4, 5)).isFalse();
        // move the pawn that is on the path of the bishop
        game.getFigureAt(3, 6).setY(5);
        assertThat(service.checkBishop(game, 2, 7, 4, 5)).isTrue();

        // can't go in front even if with move the pawn in front of the bishop
        game.getFigureAt(2, 6).setY(3);
        assertThat(service.checkBishop(game, 2, 7, 2, 5)).isFalse();

        // can't go outside the map
        assertThat(service.checkBishop(game, 2, 7, -1, 8)).isFalse();
    }

    @Test
    public void checkRookTest() {

    }

    @Test
    public void checkQueenTest() {

    }

    @Test
    public void checkKingTest() {

    }

    @Test
    public void checkKnightTest() {

    }

    @Test
    public void checkAnyTest() {

    }
}
