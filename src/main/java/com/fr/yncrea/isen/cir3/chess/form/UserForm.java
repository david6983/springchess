package com.fr.yncrea.isen.cir3.chess.form;

import com.github.ankurpathak.password.bean.constraints.ContainDigit;
import com.github.ankurpathak.password.bean.constraints.ContainSpecial;
import com.github.ankurpathak.password.bean.constraints.NotContainWhitespace;
import com.github.ankurpathak.password.bean.constraints.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserForm {
    private Long id;

    @NotEmpty
    @Size(min = 6, max = 32)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 30)
    @NotContainWhitespace
    @ContainSpecial
    @ContainDigit
    private String password;

    @NotBlank
    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
