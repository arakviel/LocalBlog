package com.arakviel.localblog.persistence.entity.impl;

import com.arakviel.localblog.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TODO: зробити валідацію по аналогії з User
 */
public class Comment extends Entity implements Comparable<Comment> {

    private final LocalDateTime createdAt;
    private String body;
    private User author;
    private LocalDateTime updatedAt;

    public Comment(UUID id, LocalDateTime createdAt, String body, User author, LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.body = body;
        this.author = author;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(Comment o) {
        return this.createdAt.compareTo(o.createdAt);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "createdAt=" + createdAt +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
