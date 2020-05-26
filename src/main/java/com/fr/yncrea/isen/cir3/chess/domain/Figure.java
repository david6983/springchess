package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;

@Entity
public class Figure {
    public static final String CODE_PREFIX = "&#";

    public static final int CODE_NUMBER = 9812;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "figures_seq_gen")
    @SequenceGenerator(name = "figures_seq_gen", sequenceName = "figures_id_seq")
    private Long id;

    @Column
    Integer code;

    @Column
    Integer x;

    @Column
    Integer y;

    @Column
    Integer killed;

    @Column
    String name;

    @Column
    int owner;

    @ManyToOne
    private Game game;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getKilled() {
        return killed;
    }

    public void setKilled(int killed) {
        this.killed = killed;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public String getHtmlCode() {
        String htmlCode = CODE_PREFIX + (CODE_NUMBER + code) + ";";

        if (owner == 1) {
            htmlCode = CODE_PREFIX + (CODE_NUMBER + code + 6) + ";";
        }

        return htmlCode;
    }
}
