package com.arakviel.localblog.persistence.entity.impl;

import com.arakviel.localblog.persistence.entity.Entity;
import com.arakviel.localblog.persistence.entity.ErrorTemplates;
import com.arakviel.localblog.persistence.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

    private final String password;
    private final LocalDate birthday;
    private final Role role;
    private String email;
    private String username;
    private String avatar;


    public User(UUID id, String password, String email, LocalDate birthday, String username,
            String avatar, Role role) {
        super(id);
        //this.password = validatedPassword(password);

        this.password = password;
        // TODO: setEmail(email);
        this.email = email;
        // TODO: validatedBirthday(birthday);
        this.birthday = birthday;
        setUsername(username);
        // TODO: setAvatar(avatar);
        this.avatar = avatar;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Setter method for the username with validation.
     * <p>
     * This method sets the username with the specified validation criteria: - Must not be empty. -
     * Must be longer than 4 characters. - Must be shorter than 24 characters. - Must consist only
     * of Latin letters.
     *
     * @param username the username to be set, must meet the validation criteria
     * @throws IllegalArgumentException if the provided username does not meet the validation
     *                                  criteria
     */
    public void setUsername(String username) {
        final String templateName = "логіну";

        if (username.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (username.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (username.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
        }
        var pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (!pattern.matcher(username).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String validatedPassword(String password) {
        final String templateName = "паролю";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (password.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (password.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        var pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$");
        if (!pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName, 24));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", id=" + id +
                '}';
    }

    public enum Role {
        ADMIN("admin", Map.of(
                EntityName.COMMENT, new Permission(true, true, true, true),
                EntityName.LIKE, new Permission(true, true, true, true),
                EntityName.POST, new Permission(true, true, true, true),
                EntityName.TAG, new Permission(true, true, true, true),
                EntityName.USER, new Permission(true, true, true, true))),
        GENERAL("general", Map.of(
                EntityName.COMMENT, new Permission(true, true, true, true),
                EntityName.LIKE, new Permission(true, false, true, true),
                EntityName.POST, new Permission(false, false, false, true),
                EntityName.TAG, new Permission(false, false, false, true),
                EntityName.USER, new Permission(false, false, false, false)));

        private final String name;
        private final Map<EntityName, Permission> permissions;

        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        public enum EntityName {COMMENT, LIKE, POST, TAG, USER}

        private record Permission(boolean canAdd, boolean canEdit, boolean canDelete,
                                  boolean canRead) {

        }
    }

}
