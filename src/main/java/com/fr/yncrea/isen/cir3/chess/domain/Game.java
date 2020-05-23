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

    public Figure getFigureAt(int x, int y) {
        for (Figure f: grid) {
            if (f.getX() == x && f.getY() == y) {
                return f;
            }
        }
        return null;
    }
}
