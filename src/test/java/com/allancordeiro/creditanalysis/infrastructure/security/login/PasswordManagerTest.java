package com.allancordeiro.creditanalysis.infrastructure.security.login;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PasswordManagerTest {
    @Test
    public void should_receive_a_valid_password() {
        PasswordManager passwordManager = new PasswordManager();
        String rawPassword = "This_is_a_password";
        String encodedPassword = passwordManager.encodePassword(rawPassword);
        Assertions.assertTrue(passwordManager.match(encodedPassword, rawPassword));
    }

    @Test
    public void should_refuse_an_invalid_password_attempt() {
        PasswordManager passwordManager = new PasswordManager();
        String rawPassword = "This_is_a_password";
        String encodedPassword = passwordManager.encodePassword(rawPassword);
        String wrongPassword = "wrong_password";
        Assertions.assertFalse(passwordManager.match(encodedPassword, wrongPassword));
    }

}