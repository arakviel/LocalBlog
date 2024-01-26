package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.SignUpService;
import com.arakviel.localblog.domain.contract.UserService;
import com.arakviel.localblog.domain.dto.UserAddDto;
import com.arakviel.localblog.domain.exception.SignUpException;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Properties;
import java.util.function.Supplier;

final class SignUpServiceImpl implements SignUpService {

    private static final int VERIFICATION_CODE_EXPIRATION_MINUTES = 1;
    private static LocalDateTime codeCreationTime;
    private final UserService userService;


    SignUpServiceImpl(UserService userService) {
        this.userService = userService;
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

    private static String generateAndSendVerificationCode(String email) {
        // Генерація 6-значного коду
        String verificationCode = String.valueOf((int) (Math.random() * 900000 + 100000));

        sendVerificationCodeEmail(email, verificationCode);

        codeCreationTime = LocalDateTime.now();

        return verificationCode;
    }

    // Перевірка введеного коду
    private static void verifyCode(String inputCode, String generatedCode) {
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

    public void signUp(UserAddDto userAddDto, Supplier<String> waitForUserInput) {
        String verificationCode = generateAndSendVerificationCode(userAddDto.email());
        String userInputCode = waitForUserInput.get();

        verifyCode(userInputCode, verificationCode);

        userService.add(userAddDto);
    }
}