package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.exception.SignUpException;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.entity.impl.User.Role;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.UUID;
import java.util.function.Supplier;
import org.mindrot.bcrypt.BCrypt;

//TODO: Винести валідацію пароля із USER!!!!
public class SignUpService {

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 1;
    private static LocalDateTime codeCreationTime;
    private final UserRepository userRepository;

    public SignUpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static String copyToImages(String avatarPath) throws IOException {
        Path source = Paths.get(avatarPath);
        String newFileName = "%s.%s".formatted(UUID.randomUUID(),
                getFileExtension(source.getFileName().toString()));
        Path target = Path.of("Images", "Avatars", newFileName);
        Files.copy(source, target);

        return target.toString();
    }

    // відправлення на пошту
    private static void sendVerificationCodeEmail(String email, String verificationCode) {
        // Властивості для конфігурації підключення до поштового сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Замініть на власний
        properties.put("mail.smtp.port", "2525"); // Замініть на власний SMTP порт
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Отримання сесії з автентифікацією
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("8c49268238c60d", "a1e3142ec54a95");
            }
        });

        try {
            // Створення об'єкта MimeMessage
            Message message = new MimeMessage(session);

            // Встановлення відправника
            message.setFrom(new InternetAddress("from@example.com")); // Замініть на власну адресу

            // Встановлення отримувача
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

            // Встановлення теми
            message.setSubject("Код підтвердження");

            // Встановлення тексту повідомлення
            message.setText("Ваш код підтвердження: " + verificationCode);

            // Відправлення повідомлення
            Transport.send(message);

            System.out.println("Повідомлення успішно відправлено.");

        } catch (MessagingException e) {
            throw new RuntimeException(
                    "Помилка при відправці електронного листа: " + e.getMessage());
        }
    }

    public static String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        sendVerificationCodeEmail(email, verificationCode);

        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }

    // Перевірка введеного коду
    public static void verifyCode(String inputCode, String generatedCode) {
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesElapsed = ChronoUnit.MINUTES.between(codeCreationTime, currentTime);

        if (minutesElapsed > VERIFICATION_CODE_EXPIRATION_MINUTES) {
            throw new SignUpException("Час верифікації вийшов. Спробуйте ще раз.");
        }

        if (!inputCode.equals(generatedCode)) {
            throw new SignUpException("Невірний код підтвердження.");
        }

        // Скидання часу створення коду
        codeCreationTime = null;
    }

    public void signUp(String username,
            String password,
            String email,
            LocalDate birthday,
            String avatarPath,
            Supplier<String> waitForUserInput) {
        signUp(username, password, email, birthday, avatarPath, Role.GENERAL, waitForUserInput);
    }

    public void signUp(String username,
            String password,
            String email,
            LocalDate birthday,
            String avatarPath,
            Role role,
            Supplier<String> waitForUserInput) {
        try {
            String verificationCode = generateAndSendVerificationCode(email);
            String userInputCode = waitForUserInput.get();

            verifyCode(userInputCode, verificationCode);

            String avatar = copyToImages(avatarPath);

            // Додаємо користувача, якщо перевірка успішна
            userRepository.add(
                    new User(UUID.randomUUID(),
                            BCrypt.hashpw(password, BCrypt.gensalt()),
                            email,
                            birthday,
                            username,
                            avatar,
                            role)
            );

        } catch (IOException e) {
            throw new SignUpException("Помилка при збереженні аватара користувача: %s"
                    .formatted(e.getMessage()));
        }
    }
}