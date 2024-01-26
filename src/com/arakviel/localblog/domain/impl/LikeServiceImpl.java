package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.LikeService;
import com.arakviel.localblog.persistence.entity.impl.Like;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.LikeRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

final class LikeServiceImpl
        extends GenericService<Like>
        implements LikeService {

    private final LikeRepository likeRepository;

    LikeServiceImpl(LikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }

    @Override
    public Set<Like> getAllByAuthor(User author) {
        return new TreeSet<>(likeRepository.findAllByAuthor(author));
    }

    @Override
    public Set<Like> getAllByPost(Post post) {
        return new TreeSet<>(likeRepository.findAllByPost(post));
    }

    @Override
    public Set<Like> getAll() {
        return getAll(c -> true);
    }

    @Override
    public Set<Like> getAll(Predicate<Like> filter) {
        return new TreeSet<>(likeRepository.findAll(filter));
    }
}
