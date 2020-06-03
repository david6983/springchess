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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "game")
    private List<Move> moves;

    @Column(nullable = false)
    private Integer currentPlayer = START_PLAYER;

    @Column
    private Long whiteKingId;

    @Column
    private Long blackKingId;

    @Column
    private Integer echec = 0;

    @Column
    private Long timeWhitePlayer;

    @Column
    private Long timeBlackPlayer;

    @Column
    private Long gameTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEchec() {
        return echec;
    }

    public void setEchec(int echec) {
        this.echec = echec;
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

    public Long getTimeCurrentPlayer() {
        if (currentPlayer == PlayerName.WHITE.ordinal()) {
            return timeWhitePlayer;
        } else if (currentPlayer == PlayerName.BLACK.ordinal()) {
            return timeBlackPlayer;
        }

        return null;
    }

    public Long getGameTime() {
        return gameTime;
    }

    public void setGameTime() {
        this.gameTime = System.currentTimeMillis();
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Long getTimeWhitePlayer() {
        return timeWhitePlayer;
    }

    public void setTimeWhitePlayer(Long timeWhitePlayer) {
        this.timeWhitePlayer = timeWhitePlayer;
    }

    public Long getTimeBlackPlayer() {
        return timeBlackPlayer;
    }

    public void setTimeBlackPlayer(Long timeBlackPlayer) {
        this.timeBlackPlayer = timeBlackPlayer;
    }

    public void setTimeCurrentPlayer(Long time) {
        if (currentPlayer == PlayerName.WHITE.ordinal()) {
            timeWhitePlayer = time;
        } else if (currentPlayer == PlayerName.BLACK.ordinal()) {
            timeBlackPlayer = time;
        }
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
