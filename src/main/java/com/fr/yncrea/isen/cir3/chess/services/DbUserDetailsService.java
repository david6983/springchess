package com.fr.yncrea.isen.cir3.chess.services;

import com.fr.yncrea.isen.cir3.chess.domain.User;
import com.fr.yncrea.isen.cir3.chess.form.UserForm;
import com.fr.yncrea.isen.cir3.chess.repository.AuthorityRepository;
import com.fr.yncrea.isen.cir3.chess.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    @Autowired
    private AuthorityRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(DbUserDetailsService.class);

    public UserForm createForm(User user) {
        UserForm form = new UserForm();
        if (user == null) {
            return form;
        }

        form.setId(user.getId());
        form.setEmail(user.getEmail());
        form.setUsername(user.getUsername());
        return form;
    }

    public void save(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setAuthorities(Collections.singletonList(roleRepository.findByAuthority("ROLE_USER")));
        users.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = users.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("user not found with the corresponding email");
        }
        return user;
    }
}
