package com.arakviel.localblog.persistence.entity.impl;

import com.arakviel.localblog.persistence.entity.Entity;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO: зробити валідацію по аналогії з User
 */
public class Tag extends Entity implements Comparable<Tag> {

    private String name;
    private String slug;

    public Tag(UUID id, String name, String slug) {
        super(id);
        this.name = name;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public int compareTo(Tag o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return Objects.equals(slug, tag.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", id=" + id +
                '}';
    }
}
