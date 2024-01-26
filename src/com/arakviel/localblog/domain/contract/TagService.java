package com.arakviel.localblog.domain.contract;

import com.arakviel.localblog.domain.Service;
import com.arakviel.localblog.persistence.entity.impl.Post;
import com.arakviel.localblog.persistence.entity.impl.Tag;
import java.util.Set;

public interface TagService extends Service<Tag> {

    Set<Tag> getAllByPost(Post post);
}
