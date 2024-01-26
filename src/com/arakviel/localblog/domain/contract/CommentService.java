package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.Service;
import com.arakviel.localblog.persistence.entity.impl.Comment;
import java.util.Set;

public interface CommentService extends Service<Comment> {

    Set<Comment> getAllByAuthor(String author);
}
