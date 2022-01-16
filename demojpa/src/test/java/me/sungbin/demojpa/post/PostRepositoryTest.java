package me.sungbin.demojpa.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({PostRepositoryTestConfig.class})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void crud_테스트() {
        Post post = new Post();
        post.setTitle("hibernate");
        assertFalse(postRepository.contains(post));

        postRepository.save(post.publish());
        assertTrue(postRepository.contains(post));

        postRepository.delete(post);
        postRepository.flush();
    }

    @Test
    void event_테스트() {
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);
    }
}