package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.CommentService;
import com.arakviel.localblog.domain.exception.EntityNotFoundException;
import com.arakviel.localblog.persistence.entity.impl.Comment;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.contracts.CommentRepository;
import com.arakviel.localblog.persistence.repository.contracts.UserRepository;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CommentServiceImpl
        extends GenericService<Comment>
        implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository) {
        super(commentRepository);
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Set<Comment> findAllByAuthor(String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Такого автора коментаря не існує"));
        return commentRepository.findAllByAuthor(author);
    }

    @Override
    public Set<Comment> getAll() {
        return getAll(c -> true);
    }

    @Override
    public Set<Comment> getAll(Predicate<Comment> filter) {
        return commentRepository.findAll(filter).stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
