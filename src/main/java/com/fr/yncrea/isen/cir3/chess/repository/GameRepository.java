package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.Game;
import com.fr.yncrea.isen.cir3.chess.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByWhitePlayerOrBlackPlayerAndIsFinish(User white, User black, Boolean isFinish);
}
