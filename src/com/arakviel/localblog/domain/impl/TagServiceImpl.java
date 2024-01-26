package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.TagService;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.Tag;
import com.arakviel.localblog.persistence.repository.contracts.TagRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

final class TagServiceImpl
        extends GenericService<Tag>
        implements TagService {

    private final TagRepository tagRepository;

    TagServiceImpl(TagRepository tagRepository) {
        super(tagRepository);
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> getAllByPost(Post post) {
        return new TreeSet<>(tagRepository.findAllByPost(post));
    }

    @Override
    public Set<Tag> getAll() {
        return getAll(p -> true);
    }

    @Override
    public Set<Tag> getAll(Predicate<Tag> filter) {
        return new TreeSet<>(tagRepository.findAll(filter));
    }
}
