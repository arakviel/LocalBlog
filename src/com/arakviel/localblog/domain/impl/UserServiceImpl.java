package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.UserService;
import com.arakviel.localblog.domain.dto.UserAddDto;
import com.arakviel.localblog.domain.exception.EntityNotFoundException;
import com.arakviel.localblog.domain.exception.SignUpException;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.apache.commons.lang3.NotImplementedException;
import org.mindrot.bcrypt.BCrypt;

final class UserServiceImpl
        extends GenericService<User>
        implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new EntityNotFoundException("Такого користувача не існує."));
    }

    @Override
    public Set<User> getAll() {
        return getAll(u -> true);
    }

    @Override
    public Set<User> getAll(Predicate<User> filter) {
        return new TreeSet<>(userRepository.findAll(filter));
    }

    @Override
    public User add(User entity) {
        throw new NotImplementedException(
                "Помилка архітектури, так як ми не використовували DTO та маппінг. "
                        + "Прошу використовувати User add(UserAddDto userAddDto) версію.");
    }

    @Override
    public User add(UserAddDto userAddDto) {
        try {
            String avatar = ImageUtil.copyToImages(userAddDto.avatarPath());
            var user = new User(userAddDto.getId(),
                    BCrypt.hashpw(userAddDto.rawPassword(), BCrypt.gensalt()),
                    userAddDto.email(),
                    userAddDto.birthday(),
                    userAddDto.username(),
                    avatar,
                    userAddDto.role());
            userRepository.add(user);
            return user;
        } catch (IOException e) {
            throw new SignUpException("Помилка при збереженні аватара користувача: %s"
                    .formatted(e.getMessage()));
        }
    }
}
