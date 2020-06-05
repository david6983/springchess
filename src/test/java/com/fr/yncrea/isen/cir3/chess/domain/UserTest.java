package com.fr.yncrea.isen.cir3.chess.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    private User u;

    @BeforeEach
    public void before() {
        u = new User();
    }

    @Test
    public void gettersTest() {

        u.setId(2048L);
        u.setLogIn(true);
        u.setUsername("User");
        u.setPlaying(false);
        u.setEmail("user@test.com");
        u.setPassword("password");
        u.setConfirmPassword("password");

        assertThat(u.getId()).isEqualTo(2048L);
        assertThat(u.getLogIn()).isTrue();
        assertThat(u.getUsername()).isEqualTo("User");
        assertThat(u.getEmail()).isEqualTo("user@test.com");
        assertThat(u.getPlaying()).isFalse();
        assertThat(u.getPassword()).isEqualTo("password");
        assertThat(u.getConfirmPassword()).isEqualTo("password");
    }

    @Test
    public void isAccountNonExpiredTest() {
        assertThat(u.isAccountNonExpired()).isTrue();
    }

    @Test
    public void isAccountNonLockedTest() {
        assertThat(u.isAccountNonLocked()).isTrue();
    }

    @Test
    public void isCredentialsNonExpiredTest() {
        assertThat(u.isCredentialsNonExpired()).isTrue();
    }

    @Test
    public void isEnabledTest() {
        assertThat(u.isEnabled()).isTrue();
    }


}
