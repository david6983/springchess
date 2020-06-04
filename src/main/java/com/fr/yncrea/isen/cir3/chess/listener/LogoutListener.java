package com.fr.yncrea.isen.cir3.chess.listener;


import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LogoutListener implements ApplicationListener<LogoutSuccessEvent> {
    @Autowired
    private UserRepository users;

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        String login = event.getAuthentication().getName();

        User u = users.findByUsername(login);
        if (u != null) {
            u.setLogIn(false);
            users.save(u);
        }
    }
}
