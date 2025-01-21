package com.arakviel.localblog;

import com.arakviel.localblog.persistence.entity.impl.Tag;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.entity.impl.User.Role;
import com.arakviel.localblog.persistence.exception.EntityArgumentException;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
        //Startup.init();
        //run();


        try {
            User user = new User(
                    UUID.randomUUID(),
                    "secure assword123",
                    "testexample.com",
                    LocalDate.of(2026, 1, 1),
                    "укр",
                    "example.com/avatar.png",
                    Role.GENERAL
            );
        } catch (EntityArgumentException e) {
            e.getErrors().forEach(System.err::println);
        }
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

    public static void run() {
        Path imagePath = Path.of("Images", "Avatars", "a677d798-1ac7-44ec-88f3-1907eb68ce6a.jpg");
        int width = 10;
        int height = 10;

        try {
            BufferedImage image = ImageIO.read(imagePath.toFile());
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();

            int consoleWidth = Math.min(width, imgWidth);
            int consoleHeight = Math.min(height, imgHeight);

            // Scale the image to fit in the console
            BufferedImage scaledImage = new BufferedImage(consoleWidth, consoleHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(image, 0, 0, consoleWidth, consoleHeight, null);
            g2d.dispose();

            // Print the scaled image to the console
            for (int y = 0; y < consoleHeight; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < consoleWidth; x++) {
                    Color color = new Color(scaledImage.getRGB(x, y));
                    sb.append(getColorCode(color));
                }
                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String getColorCode(Color color) {
        // Convert RGB color to ANSI escape code with smaller text size
        return "\u001B[2m\u001B[48;2;" + color.getRed() + ";" + color.getGreen() + ";"
                + color.getBlue()
                + "m " + "\u001B[2m\u001B[48;2;" + color.getRed() + ";" + color.getGreen() + ";"
                + color.getBlue()
                + "m ";
    }
}
