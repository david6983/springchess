package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;

@Entity
public class SuggestionCoord {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestion_coord_seq_gen")
    @SequenceGenerator(name = "suggestion_coord__seq_gen", sequenceName = "suggestion_coord__id_seq")
    private Long id;

    @Column
    private Integer x;

    @Column
    private Integer y;

    @Column
    @ManyToOne()
    private Suggestion suggestion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
