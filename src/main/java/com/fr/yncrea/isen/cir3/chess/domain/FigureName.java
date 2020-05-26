package com.fr.yncrea.isen.cir3.chess.domain;

public enum FigureName {
    KING(0),
    QUEEN(1),
    ROOK(2),
    BISHOP(3),
    KNIGHT(4),
    PAWN(5);


    FigureName(int value) {

    }

    public static FigureName stringToFigureName(String name) {
        FigureName out = null;

        switch(name) {
            case "rook":
                out = FigureName.ROOK;
                break;
            case "king":
                out = FigureName.KING;
                break;
            case "queen":
                out = FigureName.QUEEN;
                break;
            case "bishop":
                out = FigureName.BISHOP;
                break;
            case "pawn":
                out = FigureName.PAWN;
                break;
            case "knight":
                out = FigureName.KNIGHT;
                break;
        }

        return out;
    }
}
