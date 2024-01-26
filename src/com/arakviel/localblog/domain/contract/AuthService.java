package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.persistence.entity.impl.User;

public interface AuthService {

    boolean authenticate(String username, String password);

    boolean isAuthenticated();

    User getUser();

    void logout();
}
