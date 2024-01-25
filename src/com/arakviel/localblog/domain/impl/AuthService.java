package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.exception.AuthException;
import com.arakviel.localblog.domain.exception.UserAlreadyAuthException;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import org.mindrot.bcrypt.BCrypt;

public class AuthService {

    private final UserRepository userRepository;
    private User user;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        // Перевіряємо, чи вже існує аутентифікований користувач
        if (user != null) {
            throw new UserAlreadyAuthException("Ви вже авторизувалися як: %s"
                    .formatted(user.getUsername()));
        }

        User foundedUser = userRepository.findByUsername(username)
                .orElseThrow(AuthException::new);

        if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
            return false;
        }

        user = foundedUser;
        return true;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthException("Ви ще не автентифікавані.");
        }
        user = null;
    }
}