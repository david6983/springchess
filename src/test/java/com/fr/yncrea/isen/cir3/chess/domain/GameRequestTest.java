package com.fr.yncrea.isen.cir3.chess.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameRequestTest {
    private GameRequest g;

    @BeforeEach
    public void before() {
        g = new GameRequest();
    }

    @Test
    public void gettersTest() {
        User u1 = new User();
        User u2 = new User();

        g.setId(2048L);
        g.setSender(u1);
        g.setReceiver(u2);
        g.setAccepted(true);

        assertThat(g.getId()).isEqualTo(2048L);
        assertThat(g.getSender()).isEqualTo(u1);
        assertThat(g.getAccepted()).isTrue();
        assertThat(g.getReceiver()).isEqualTo(u2);

    }
}
