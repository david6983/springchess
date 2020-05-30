package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.domain.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
}

