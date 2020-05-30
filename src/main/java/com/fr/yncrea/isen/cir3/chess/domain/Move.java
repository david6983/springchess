package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;

@Entity
public class Move {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moves_seq_gen")
    @SequenceGenerator(name = "moves_seq_gen", sequenceName = "moves_id_seq")
    private Long id;

    @Column
    private String code;

    @Column
    private Integer player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }
}
