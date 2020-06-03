package com.fr.yncrea.isen.cir3.chess.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Suggestion {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suggestion_seq_gen")
    @SequenceGenerator(name = "suggestion_seq_gen", sequenceName = "suggestion_id_seq")
    private Long id;

    @OneToOne
    private Figure figure;

    @Column
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "suggestion")
    private Set<SuggestionCoord> moves;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SuggestionCoord> getMoves() {
        return moves;
    }

    public void setMoves(Set<SuggestionCoord> moves) {
        this.moves = moves;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
