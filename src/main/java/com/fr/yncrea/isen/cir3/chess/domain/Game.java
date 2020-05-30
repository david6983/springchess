package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Game {
    public static final int NUMBER_OF_PLAYER_IN_GAME = 2;

    public static final int START_PLAYER = PlayerName.WHITE.ordinal();

    public static final List<String> FIGURES_PLACEMENT = Arrays.asList(
            "rook",
            "knight",
            "bishop",
            "queen",
            "king",
            "bishop",
            "knight",
            "rook"
    );

    public static final List<String> FIGURES_PROMOTION = Arrays.asList(
            "rook",
            "knight",
            "bishop",
            "queen"
    );

    /**
     * Default width of a chess grid.
     */
    public static final int WIDTH = 8;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "games_seq_gen")
    @SequenceGenerator(name = "games_seq_gen", sequenceName = "games_id_seq")
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "game")
    private List<Figure> grid;

    @Column(nullable = false)
    private Integer currentPlayer = START_PLAYER;

    @Column
    private Long whiteKingId;

    @Column
    private Long blackKingId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Figure> getGrid() {
        return grid;
    }

    public void setGrid(List<Figure> grid) {
        this.grid = grid;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void changePlayer() {
        this.currentPlayer = 1 - this.currentPlayer;
    }

    public Long getWhiteKingId() {
        return whiteKingId;
    }

    public void setWhiteKingId(Long whiteKingId) {
        this.whiteKingId = whiteKingId;
    }

    public Long getBlackKingId() {
        return blackKingId;
    }

    public void setBlackKingId(Long blackKingId) {
        this.blackKingId = blackKingId;
    }

    public Figure getFigureAt(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return null;
        }

        for (Figure f: grid) {
            if (f.getX() == x && f.getY() == y) {
                return f;
            }
        }
        return null;
    }

    public Figure getFigureById(Long id) {
        for (Figure f: grid) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public boolean isCellFree(int x, int y) {
        return getFigureAt(x, y) == null;
    }

    public int getNumberOfPlay(int player) {
        int count = 0;

        for (Figure f: grid) {
            if (player == f.getOwner()) {
                count = count + f.getCountPlayed();
            }
        }

        return count;
    }
}
