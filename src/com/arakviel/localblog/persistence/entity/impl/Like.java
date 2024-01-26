package com.arakviel.localblog.persistence.entity.impl;

import com.arakviel.localblog.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * TODO: зробити валідацію по аналогії з User
 */
public class Like extends Entity implements Comparable<Like> {

    private final LocalDateTime createdAt;
    private Post post;
    private User author;
    private LocalDateTime updatedAt;

    public Like(UUID id, LocalDateTime createdAt, Post post, User author,
            LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.post = post;
        this.author = author;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
    public String toString() {
        return "Like{" +
                "createdAt=" + createdAt +
                ", post=" + post +
                ", author=" + author +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }

    @Override
    public int compareTo(Like o) {
        return this.createdAt.compareTo(o.createdAt);
    }
}
