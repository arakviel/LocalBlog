package com.arakviel.localblog;

import com.arakviel.localblog.domain.exception.SignUpException;
import com.arakviel.localblog.domain.impl.SignUpService;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.entity.impl.User.Role;
import com.arakviel.localblog.persistence.repository.RepositoryFactory;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
       /* Set<User> users = generateUsers(10);

        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        UserRepository userRepository = jsonRepositoryFactory.getUserRepository();

        // Виведемо створених користувачів

        int i = 0;
        for (User user : users) {
            userRepository.add(user);
            if (i == 3) {
                userRepository.remove(user);
            }
            if (i == 5) {
                userRepository.remove(user);
            }
            if (i == 7) {
                userRepository.remove(user);
            }
            i++;
        }

        userRepository.findAll().forEach(out::println);

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();*/

        //SignUpService.generateAndSendVerificationCode("To@example.com");

        // Перевірка реєстрації
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        UserRepository userRepository = jsonRepositoryFactory.getUserRepository();
        SignUpService signUpService = new SignUpService(userRepository);
        try {
            signUpService.signUp("user2", "password", "example@mail.com",
                    LocalDate.of(2000, 1, 1), "C:/Users/insid/Downloads/avatar.jpg",
                    () -> {
                        System.out.print("Введіть код підтвердження: ");
                        try (Scanner scanner = new Scanner(System.in)) {
                            return scanner.nextLine();
                        } /*catch (Exception e) {
                        System.out.println("Помилка формату.");

                    }*/
                    });
        } catch (SignUpException e) {
            System.err.println(e.getMessage());
        }

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
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
