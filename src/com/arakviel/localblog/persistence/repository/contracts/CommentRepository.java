package com.arakviel.localblog.persistence.repository.contracts;

import com.arakviel.localblog.persistence.entity.impl.Comment;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.Repository;
import java.util.Set;

public interface CommentRepository extends Repository<Comment> {

    Set<Comment> findAllByAuthor(User author);
}
