package com.fr.yncrea.isen.cir3.chess.repository;

import com.fr.yncrea.isen.cir3.chess.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(String name);
}
