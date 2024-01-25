package com.arakviel.localblog.persistence.repository.impl.json;

import com.arakviel.localblog.persistence.entity.impl.Like;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.LikeRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

class LikeJsonRepositoryImpl
        extends GenericJsonRepository<Like>
        implements LikeRepository {

    public LikeJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.LIKES.getPath(), TypeToken
                .getParameterized(Set.class, Like.class)
                .getType());
    }

    @Override
    public Set<Like> findAllByAuthor(User author) {
        return entities.stream().filter(l -> l.getAuthor().equals(author))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Like> findAllByPost(Post post) {
        return entities.stream().filter(l -> l.getPost().equals(post)).collect(Collectors.toSet());
    }
}
