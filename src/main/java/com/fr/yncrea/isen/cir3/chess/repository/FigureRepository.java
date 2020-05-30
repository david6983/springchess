package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.Figure;
import com.fr.yncrea.isen.cir3.chess.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FigureRepository extends JpaRepository<Figure, Long> {
    long deleteByGame(Game game);
}
