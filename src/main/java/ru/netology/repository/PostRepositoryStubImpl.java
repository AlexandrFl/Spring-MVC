package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    private List<Post> list = new LinkedList<>();
    private long idCount = 1;

    public List<Post> all() {
        List<Post> posts = new LinkedList<>();
        for (Post post : list) {
            if (!post.getRemoveStatus()) {
                posts.add(post);
            }
        }
        return posts;
    }

    public Optional<Post> getById(long id) {
        if (!list.isEmpty()) {
            for (Post post : list) {
                if (post.getId() == id && !post.getRemoveStatus()) { //default RemoveStatus - false
                    return Optional.of(post);
                }
                if (post.getId() == id && post.getRemoveStatus()) {
                    throw new NotFoundException();
                }
            }
        }
        return Optional.empty();
    }


    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(idCount++);
            list.add(post);
            return post;
        }
        for (Post posts : list) {
            if (posts.getId() == post.getId() && !posts.getRemoveStatus()) {
                posts.setId(post.getId());
                posts.setContent(post.getContent());
                return posts;
            }
            if (posts.getId() == post.getId() && posts.getRemoveStatus()) {
                throw new NotFoundException();
            }
        }
        post.setId(idCount++);
        list.add(post);
        return post;
    }

    public void removeById(long id) {
        for (Post post : list) {
            if (post.getId() == id && !post.getRemoveStatus()) {
                post.changeRemoveStatus();
            }
        }
    }
}
