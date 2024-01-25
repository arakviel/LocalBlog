package com.arakviel.localblog.persistence.repository.impl.json;

import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.repository.contracts.PostRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

class PostJsonRepositoryImpl
        extends GenericJsonRepository<Post>
        implements PostRepository {

    public PostJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.POSTS.getPath(), TypeToken
                .getParameterized(Set.class, Post.class)
                .getType());
    }

    @Override
    public Set<Post> getAllPublished() {
        return entities.stream().filter(Post::isPublished).collect(Collectors.toSet());
    }

    @Override
    public Set<Post> getAllByTitle(String title) {
        return entities.stream().filter(p -> p.getTitle().equals(title))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Post> getAllByDescription(String description) {
        return entities.stream().filter(p -> p.getDescription().equals(description))
                .collect(Collectors.toSet());
    }
}
