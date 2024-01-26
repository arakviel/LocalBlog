package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.Service;
import com.arakviel.localblog.domain.dto.UserAddDto;
import com.arakviel.localblog.persistence.entity.impl.User;

public interface UserService extends Service<User> {

    User getByUsername(String username);

    User getByEmail(String email);

    User add(UserAddDto userAddDto);
}
