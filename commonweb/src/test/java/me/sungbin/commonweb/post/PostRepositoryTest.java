package me.sungbin.commonweb.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 예외_테스트() {
        Post post = new Post();
        post.setTitle("jpa");
        postRepository.save(post);

        List<Post> all = postRepository.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void save_테스트() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);// insert (persist)

        assertTrue(entityManager.contains(post));
        assertTrue(entityManager.contains(savedPost));
        assertEquals(savedPost, post);

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post updatePost = postRepository.save(postUpdate);// update (merge)

        updatePost.setTitle("sungbin");

        assertTrue(entityManager.contains(updatePost));
        assertFalse(entityManager.contains(postUpdate));
        assertNotEquals(updatePost, postUpdate);

        List<Post> all = postRepository.findAll(); // select
        assertEquals(1, all.size());
    }
}