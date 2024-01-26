package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.PostService;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.repository.contracts.PostRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

final class PostServiceImpl
        extends GenericService<Post>
        implements PostService {


    private final PostRepository postRepository;

    PostServiceImpl(PostRepository postRepository) {
        super(postRepository);
        this.postRepository = postRepository;
    }

    @Override
    public Set<Post> getAllPublished() {
        return new TreeSet<>(postRepository.findAllPublished());
    }

    @Override
    public Set<Post> getAllByTitle(String title) {
        return new TreeSet<>(postRepository.findAllByTitle(title));
    }

    @Override
    public Set<Post> getAllByDescription(String description) {
        return new TreeSet<>(postRepository.findAllByDescription(description));
    }

    @Override
    public Set<Post> getAll() {
        return getAll(p -> true);
    }

    @Override
    public Set<Post> getAll(Predicate<Post> filter) {
        return new TreeSet<>(postRepository.findAll(filter));
    }
}
