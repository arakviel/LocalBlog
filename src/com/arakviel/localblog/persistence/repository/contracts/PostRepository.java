package com.arakviel.localblog.persistence.repository.contracts;

import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.repository.Repository;
import java.util.Set;

public interface PostRepository extends Repository<Post> {

    Set<Post> findAllPublished();

    Set<Post> findAllByTitle(String title);

    Set<Post> findAllByDescription(String description);
}
