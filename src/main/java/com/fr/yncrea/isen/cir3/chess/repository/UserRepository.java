package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
