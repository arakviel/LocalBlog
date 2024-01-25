package com.arakviel.localblog.persistence.repository.contracts;

import com.arakviel.localblog.persistence.entity.impl.Like;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.User;
import com.arakviel.localblog.persistence.repository.Repository;
import java.util.Set;

public interface LikeRepository extends Repository<Like> {

    Set<Like> findAllByAuthor(User author);

    Set<Like> findAllByPost(Post post);
}
