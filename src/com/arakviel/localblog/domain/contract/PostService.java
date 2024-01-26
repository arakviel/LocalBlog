package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.Service;
import com.arakviel.localblog.persistence.entity.impl.Post;
import java.util.Set;

public interface PostService extends Service<Post> {

    Set<Post> getAllPublished();

    Set<Post> getAllByTitle(String title);

    Set<Post> getAllByDescription(String description);
}
