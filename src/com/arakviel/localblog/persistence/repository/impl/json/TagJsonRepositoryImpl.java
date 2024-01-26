package com.arakviel.localblog.persistence.repository.impl.json;

import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.Tag;
import com.arakviel.localblog.persistence.repository.contracts.TagRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;
import java.util.stream.Collectors;

final class TagJsonRepositoryImpl
        extends GenericJsonRepository<Tag>
        implements TagRepository {

    TagJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.TAGS.getPath(), TypeToken
                .getParameterized(Set.class, Tag.class)
                .getType());
    }

    @Override
    public Set<Tag> findAllByPost(Post post) {
        return entities.stream()
                .filter(t -> post.getTags().contains(t))
                .collect(Collectors.toSet());
    }
}
