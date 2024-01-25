package com.arakviel.localblog.persistence.repository;

import com.arakviel.localblog.persistence.repository.contracts.CommentRepository;
import com.arakviel.localblog.persistence.repository.contracts.LikeRepository;
import com.arakviel.localblog.persistence.repository.contracts.PostRepository;
import com.arakviel.localblog.persistence.repository.contracts.TagRepository;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import com.arakviel.localblog.persistence.repository.impl.json.JsonRepositoryFactory;
import org.apache.commons.lang3.NotImplementedException;

public abstract class RepositoryFactory {

    public static final int JSON = 1;
    public static final int XML = 2;
    public static final int POSTGRESQL = 3;

    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        return switch (whichFactory) {
            case JSON -> JsonRepositoryFactory.getInstance();
            case XML -> throw new NotImplementedException("Робота з XML файлами не реалізована.");
            case POSTGRESQL -> throw new NotImplementedException(
                    "Робота з СУБД PostgreSQL не реалізована.");
            default -> throw new IllegalArgumentException(
                    "Помилка при виборі фабрики репозиторіїв.");
        };
    }

    public abstract CommentRepository getCommentRepository();

    public abstract LikeRepository getLikeRepository();

    public abstract PostRepository getPostRepository();

    public abstract TagRepository getTagRepository();

    public abstract UserRepository getUserRepository();

    public abstract void commit();
}
