package com.arakviel.localblog.persistence.repository.contracts;

import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.repository.Repository;
import java.util.Set;

public interface PostRepository extends Repository<Post> {

    Set<Post> getAllPublished();

    Set<Post> getAllByTitle(String title);

    Set<Post> getAllByDescription(String description);
}
