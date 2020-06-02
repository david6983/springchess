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
    private String positionEnd;

    @Column
    private String positionStart;

    @Column
    private Integer player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionEnd() {
        return positionEnd;
    }

    public void setPositionEnd(String positionEnd) {
        this.positionEnd = positionEnd;
    }

    public String getPositionStart() {
        return positionStart;
    }

    public void setPositionStart(String positionStart) {
        this.positionStart = positionStart;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }
}
