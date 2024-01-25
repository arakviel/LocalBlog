package com.arakviel.localblog.persistence.repository.impl.json;

import com.arakviel.localblog.persistence.entity.impl.Comment;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.CommentRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

class CommentJsonRepositoryImpl
        extends GenericJsonRepository<Comment>
        implements CommentRepository {

    public CommentJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.COMMENTS.getPath(), TypeToken
                .getParameterized(Set.class, Comment.class)
                .getType());
    }

    @Override
    public Set<Comment> findAllByAuthor(User author) {
        return entities.stream().filter(c -> c.getAuthor().equals(author))
                .collect(Collectors.toSet());
    }
}
