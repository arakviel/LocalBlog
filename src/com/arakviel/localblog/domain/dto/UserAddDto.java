package com.arakviel.localblog.domain.dto;

import com.arakviel.localblog.persistence.entity.Entity;
import com.arakviel.localblog.persistence.entity.ErrorTemplates;
import com.arakviel.localblog.persistence.entity.impl.User.Role;
import com.arakviel.localblog.persistence.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public final class UserAddDto extends Entity {

    private final String username;
    private final String rawPassword;
    private final String email;
    private final LocalDate birthday;
    private final String avatarPath;
    private final Role role;

    public UserAddDto(UUID id,
            String username,
            String rawPassword,
            String email,
            LocalDate birthday,
            String avatarPath) {
        super(id);
        this.username = username;
        this.rawPassword = validatedPassword(rawPassword);
        this.email = email;
        this.birthday = birthday;
        this.avatarPath = avatarPath;
        this.role = Role.GENERAL;
    }

    public UserAddDto(UUID id,
            String username,
            String rawPassword,
            String email,
            LocalDate birthday,
            String avatarPath,
            Role role) {
        super(id);
        this.username = username;
        this.rawPassword = validatedPassword(rawPassword);
        this.email = email;
        this.birthday = birthday;
        this.avatarPath = avatarPath;
        this.role = role;
    }

    private String validatedPassword(String rawPassword) {
        final String templateName = "паролю";

        if (rawPassword.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (rawPassword.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (rawPassword.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        if (!pattern.matcher(rawPassword).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return rawPassword;
    }

    public String username() {
        return username;
    }

    public String rawPassword() {
        return rawPassword;
    }

    public String email() {
        return email;
    }

    public LocalDate birthday() {
        return birthday;
    }

    public String avatarPath() {
        return avatarPath;
    }

    public Role role() {
        return role;
    }
}
