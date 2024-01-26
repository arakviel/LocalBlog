package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.Service;
import com.arakviel.localblog.persistence.entity.impl.Like;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.User;
import java.util.Set;

public interface LikeService extends Service<Like> {

    Set<Like> getAllByAuthor(User author);

    Set<Like> getAllByPost(Post post);
}
