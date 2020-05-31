package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        fig.setName(name);
        fig.setOwner(owner);
        fig.setGame(game);

        grid.add(fig);

    }

    /**
     * generate a grid of chess with all the pawns
     *
     * @param game game that contains the grid
     */
    public void generateGrid(final Game game) {
        // create a list of Figures
        List<Figure> grid = new ArrayList<>();

        for (int i = 0; i < Game.NUMBER_OF_PLAYER_IN_GAME; i++) {
            for (int j = 0; j < Game.WIDTH; j++) {
                String figName = Game.FIGURES_PLACEMENT.get(j);

                addFigureToGrid(grid, game, j, 5 * (1 - i) + 1, FigureName.PAWN.ordinal(), "pawn", i);
                addFigureToGrid(grid, game, j, 7 * (1 - i), FigureName.valueOf(figName.toUpperCase()).ordinal(), figName, i);
            }
        }
        // add the grid to the game
        game.setGrid(grid);
        logger.info("grid successfully generated");
    }

    /**
     * Return the sign of a number
     *
     * @param v number to test
     * @return 1 if positive, -1 if negative, 0 otherwise
     */
    public int isPositive(int v) {
        return Integer.compare(v, 0);
    }

    public boolean isSegmentFree(Game game, int x1, int y1, int x2, int y2) {
        int dx = isPositive(x2 - x1);
        int dy = isPositive(y2 - y1);

        // ignore the last cell
        x2 -= dx;
        y2 -= dy;

        while (x1 != x2 || y1 != y2) {
            x1 += dx;
            y1 += dy;
            if (!game.isCellFree(x1, y1)) {
                return false;
            }
        }

        return true;
    }

    public boolean checkBishop(Game game, int x, int y, int nx, int ny) {
        if (Math.abs(x - nx) == Math.abs(y - ny)) {
            return isSegmentFree(game, x, y, nx, ny);
        }

        return false;
    }

    public boolean checkRook(Game game, int x, int y, int nx, int ny) {
        if (x == nx || y == ny) {
            return isSegmentFree(game, x, y, nx, ny);
        }

        return false;
    }

    public boolean checkQueen(Game game, int x, int y, int nx, int ny) {
        return checkBishop(game, x, y, nx, ny) || checkRook(game, x, y, nx, ny);
    }

    public boolean checkKing(Game game, Figure f1, int nx, int ny) {
        if (checkBigCastling(game, f1, nx, ny)) {
            startCastling(game, 0, 3);
            return true;
        } else if (checkSmallCastling(game, f1, nx, ny)) {
            startCastling(game, 7, 5);
            return true;
        } else {
            return Math.abs(f1.getX() - nx) <= 1 && Math.abs(f1.getY() - ny) <= 1;
        }
    }

    public boolean checkKnight(int x, int y, int nx, int ny) {
        return (Math.abs(x - nx) == 1 && Math.abs(y - ny) == 2) ||
                (Math.abs(x - nx) == 2 && Math.abs(y - ny) == 1);
    }

    public boolean checkPawn(Game game, Figure f, int nx, int ny) {
        // get the owner of the figure
        int owner = f.getOwner();
        // compute the direction according to the owner
        int dy = Arrays.asList(-1, 1).get(owner);
        // y offset
        int py = f.getY() + dy;
        // get x of the figure
        int x = f.getX();

        // the move is strictly forward
        if (nx == x) {
            // verify the target cell is free and the pawn move by +1 on;y
            if (game.isCellFree(nx, ny) && ny == py) {
                return true;
            } else if (f.getY() == (5 * (1 - owner) + 1) && ny == (py + dy)) { // manage the move +2 at the beginning
                return game.isCellFree(x, py) && game.isCellFree(x, py + dy);
            }
        } else if (Math.abs(nx - x) == 1 && ny == py) { // the move is in diagonal
            if (checkEnPassant(game, f, nx, ny)) {
                game.getGrid().remove(game.getFigureAt(f.getX() + 1, f.getY())); //TODO Fix
                return true;
            }

            return game.getFigureAt(nx, ny) != null;
        }

        return false;
    }

    public boolean checkEnPassant(Game game, Figure pawn, int nx, int ny) {
        int player = game.getCurrentPlayer();
        // Theoretical pawn position to realize an en passant.
        int yPawn = (pawn.getOwner() == PlayerName.BLACK.ordinal()) ? 4 : 3;
        if (pawn.getY() == yPawn) {
            // verify the target cell is free
            if (game.getFigureAt(nx, ny) == null) {
                // verify the opposite pawn played only once and it position is on the right of the pawn
                Figure oppositePawn = game.getFigureAt(pawn.getX() + 1, pawn.getY());
                return oppositePawn.getOwner() == (1 - player) && oppositePawn.getCountPlayed() == 1;
            }
        }

        return false;
    }

    public boolean checkAny(Game game, Figure f1, int dx, int dy) {
        FigureName name = FigureName.stringToFigureName(f1.getName());
        int x = f1.getX();
        int y = f1.getY();
        boolean check = false;

        switch (name) {
            case KING:
                check = checkKing(game, f1, dx, dy);
                break;
            case QUEEN:
                check = checkQueen(game, x, y, dx, dy);
                break;
            case BISHOP:
                check = checkBishop(game, x, y, dx, dy);
                break;
            case ROOK:
                check = checkRook(game, x, y, dx, dy);
                break;
            case KNIGHT:
                check = checkKnight(x, y, dx, dy);
                break;
            case PAWN:
                check = checkPawn(game, f1, dx, dy);
                break;
        }

        return check;
    }

    public boolean checkSmallCastling(Game game, Figure f1, int dx, int dy) {
        // Theoretical king's starting position
        int yKing = (f1.getOwner() == PlayerName.BLACK.ordinal()) ? 0 : 7;
        int xKing = 4;

        // verify the king is at it starting point
        if (f1.getX() == xKing && f1.getY() == yKing && f1.getCountPlayed() == 0) {
            // verify the right rook is at it starting point
            if (game.getFigureAt(7, yKing) != null && game.getFigureAt(7, yKing).getCountPlayed() == 0) {
                // verify the segment is free between the king and the rook
                isSegmentFree(game, f1.getX(), f1.getY(), 7, yKing);
                // verify the destination of the king
                return dx == 6 && dy == yKing;
            }
        }

        return false;
    }

    public boolean checkBigCastling(Game game, Figure f1, int dx, int dy) {
        // Theoretical king's starting position
        int yKing = (f1.getOwner() == PlayerName.BLACK.ordinal()) ? 0 : 7;
        int xKing = 4;

        // verify the king is at it starting point
        if (f1.getX() == xKing && f1.getY() == yKing && f1.getCountPlayed() == 0) {
            // verify the right rook is at it starting point
            if (game.getFigureAt(7, yKing) != null && game.getFigureAt(7, yKing).getCountPlayed() == 0) {
                // verify the segment is free between the king and the rook
                isSegmentFree(game, f1.getX(), f1.getY(), 1, yKing);
                // verify the destination of the king
                return dx == 2 && dy == yKing;
            }
        }

        return false;
    }

    public void startCastling(Game game, int xRook, int dxRook) {
        int player = game.getCurrentPlayer();
        int yRook = (player == PlayerName.BLACK.ordinal()) ? 0 : 7;
        // change the position of the left rook
        game.getFigureAt(xRook, yRook).setX(dxRook);
    }

    public boolean enablePromotePawn(Figure f) {
        return (FigureName.stringToFigureName(f.getName()) == FigureName.PAWN && (f.getY() == 0 || f.getY() == 7));
    }

    public void findKing(Game game) {
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.WIDTH; j++) {
                if (game.getFigureAt(i, j) != null && game.getFigureAt(i, j).getName().equals("king")) {
                    if (game.getFigureAt(i, j).getOwner() == 0) {
                        game.setWhiteKingId(game.getFigureAt(i, j).getId());
                    } else {
                        game.setBlackKingId(game.getFigureAt(i, j).getId());
                    }
                }
            }
        }

    }


    public boolean checkEchec(Game game) {
        if (game.getFigureById(game.getWhiteKingId()) != null && game.getFigureById(game.getBlackKingId()) != null) {
            int xKing, yKing;
            int player = game.getCurrentPlayer();
            boolean response = false;
            if (player == 0) {
                xKing = game.getFigureById(game.getWhiteKingId()).getX();
                yKing = game.getFigureById(game.getWhiteKingId()).getY();
            } else {
                xKing = game.getFigureById(game.getBlackKingId()).getX();
                yKing = game.getFigureById(game.getBlackKingId()).getY();
            }
            for (int i = 0; i < Game.WIDTH; i++) {
                for (int j = 0; j < Game.WIDTH; j++) {
                    if (game.getFigureAt(i, j) != null && game.getFigureAt(i, j).getOwner() != player) {
                        if (response) {
                            System.out.println("ECHEC " + FigureName.stringToFigureName(game.getFigureAt(i, j).getName()));
                            return true;
                        } else
                            response = checkAny(game, game.getFigureAt(i, j), xKing, yKing);
                    }
                }
            }
        }
        return false;
    }

    public Boolean checkMate(Game game) {
        if (game.getFigureById(game.getWhiteKingId()) != null && game.getFigureById(game.getBlackKingId()) != null) {
            int xKing, yKing;
            int player = game.getCurrentPlayer();
            boolean response = false;
            if (player == 1) {
                xKing = game.getFigureById(game.getWhiteKingId()).getX();
                yKing = game.getFigureById(game.getWhiteKingId()).getY();
            } else {
                xKing = game.getFigureById(game.getBlackKingId()).getX();
                yKing = game.getFigureById(game.getBlackKingId()).getY();
            }
            for (int i = 0; i < Game.WIDTH; i++) {
                for (int j = 0; j < Game.WIDTH; j++) {
                    if (game.getFigureAt(i, j) != null && game.getFigureAt(i, j).getOwner() == player) {
                        if (response) {
                            System.out.println("MATE " + FigureName.stringToFigureName(game.getFigureAt(i, j).getName()));
                            return true;
                        } else
                            response = checkAny(game, game.getFigureAt(i, j), xKing, yKing);
                    }
                }
            }
        }else {
            return true;
        }
        return false;
    }

}
