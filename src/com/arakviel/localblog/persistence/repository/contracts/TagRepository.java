package com.arakviel.localblog.persistence.repository.contracts;

import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.Tag;
import com.arakviel.localblog.persistence.repository.Repository;
import java.util.Set;

public interface TagRepository extends Repository<Tag> {

    Set<Tag> getAllByPost(Post post);
}
