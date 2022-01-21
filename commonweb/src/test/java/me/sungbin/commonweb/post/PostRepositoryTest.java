package me.sungbin.commonweb.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

    @Test
    void findByTitleStartsWith_테스트() {
        savePost();

        List<Post> all = postRepository.findByTitleStartingWith("Spring");
        assertEquals(1, all.size());
    }

    @Test
    void findByTitle_테스트() {
        savePost();

        List<Post> all = postRepository.findByTitle("Spring", Sort.by("title"));
        assertEquals(1, all.size());

        List<Post> other = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));
        assertEquals(1, other.size());
    }

    @Test
    void updateTitle_테스트() {
        Post spring = savePost();
        int update = postRepository.updateTitle("hibernate", spring.getId());
        assertEquals(1, update);

        Optional<Post> byId = postRepository.findById(spring.getId()); // select query가 나타나지 않 :: 1차캐시로 인하여
        assertEquals("hibernate", byId.get().getTitle());
    }

    private Post savePost() {
        Post post = new Post();
        post.setTitle("Spring");
        return postRepository.save(post);
    }
}