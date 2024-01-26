package com.arakviel.localblog.domain.impl;

import com.arakviel.localblog.domain.contract.AuthService;
import com.arakviel.localblog.domain.contract.CommentService;
import com.arakviel.localblog.domain.contract.LikeService;
import com.arakviel.localblog.domain.contract.PostService;
import com.arakviel.localblog.domain.contract.SignUpService;
import com.arakviel.localblog.domain.contract.UserService;
import com.arakviel.localblog.domain.exception.DependencyException;
import com.arakviel.localblog.persistence.repository.RepositoryFactory;

public final class ServiceFactory {

    private static volatile ServiceFactory INSTANCE;
    private final AuthService authService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final PostService postService;
    private final UserService userService;
    private final SignUpService signUpService;
    private final RepositoryFactory repositoryFactory;

    private ServiceFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
        var userRepository = repositoryFactory.getUserRepository();
        authService = new AuthServiceImpl(userRepository);
        commentService = new CommentServiceImpl(
                repositoryFactory.getCommentRepository(),
                userRepository
        );
        likeService = new LikeServiceImpl(repositoryFactory.getLikeRepository());
        postService = new PostServiceImpl(repositoryFactory.getPostRepository());
        userService = new UserServiceImpl(userRepository);
        signUpService = new SignUpServiceImpl(userService);
    }

    /**
     * Використовувати, лише якщо впевнені, що існує об'єкт RepositoryFactory.
     *
     * @return екземпляр типу ServiceFactory
     */
    public static ServiceFactory getInstance() {
        if (INSTANCE.repositoryFactory != null) {
            return INSTANCE;
        } else {
            throw new DependencyException(
                    "Ви забули створити обєкт RepositoryFactory, перед використанням ServiceFactory.");
        }
    }

    public static ServiceFactory getInstance(RepositoryFactory repositoryFactory) {
        if (INSTANCE == null) {
            synchronized (ServiceFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceFactory(repositoryFactory);
                }
            }
        }

        return INSTANCE;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public CommentService getCommentService() {
        return commentService;
    }

    public LikeService getLikeService() {
        return likeService;
    }

    public PostService getPostService() {
        return postService;
    }

    public UserService getUserService() {
        return userService;
    }

    public SignUpService getSignUpService() {
        return signUpService;
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }
}
