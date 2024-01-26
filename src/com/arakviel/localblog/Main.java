package com.arakviel.localblog;

import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.entity.impl.User.Role;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Startup.init();
    }

    public static Set<User> generateUsers(int count) {
        Set<User> users = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID userId = UUID.randomUUID();
            String password = faker.internet().password();
            String email = faker.internet().emailAddress();
            LocalDate birthday = faker.date()
                    .birthday()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            String username = faker.name().username();
            String avatar = faker.internet().avatar();

            User user = new User(userId, password, email, birthday, username, avatar,
                    Role.valueOf("ADMIN"));
            users.add(user);
        }

        return users;
    }
}
