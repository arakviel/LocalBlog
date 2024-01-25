package com.arakviel.localblog.persistence.entity.impl;

import com.arakviel.localblog.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * TODO: зробити валідацію по аналогії з User
 */
public class Post extends Entity implements Comparable<Post> {

    private final LocalDateTime createdAt;
    private String title;
    private String description;
    private String body;
    private String image;
    private boolean isPublished;
    private LocalDateTime updatedAt;
    private User author;
    private Set<Tag> tags;

    public Post(UUID id, LocalDateTime createdAt, String title, String description,
            String body, String image, boolean isPublished, LocalDateTime updatedAt, User author,
            Set<Tag> tags) {
        super(id);
        this.createdAt = createdAt;
        this.title = title;
        this.description = description;
        this.body = body;
        this.image = image;
        this.isPublished = isPublished;
        this.updatedAt = updatedAt;
        this.author = author;
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public int compareTo(Post o) {
        return this.createdAt.compareTo(o.createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(title, post.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Post{" +
                "createdAt=" + createdAt +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                ", image='" + image + '\'' +
                ", isPublished=" + isPublished +
                ", updatedAt=" + updatedAt +
                ", author=" + author +
                ", tags=" + tags +
                ", id=" + id +
                '}';
    }
}
