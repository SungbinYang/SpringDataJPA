package me.sungbin.demojpa.post;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({PostRepositoryTestConfig.class})
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void crud() {
        Post post = new Post();
        post.setTitle("hibernate");
        assertFalse(postRepository.contains(post));

        postRepository.save(post.publish());
        assertTrue(postRepository.contains(post));

        postRepository.delete(post);
        postRepository.flush();
    }

    @Test
    public void querydsl() {
        Post post = new Post();
        post.setTitle("querydsl");
        postRepository.save(post.publish());

        Predicate predicate = QPost.post.title.containsIgnoreCase("querydsl");
        Optional<Post> one = postRepository.findOne(predicate);
        assertThat(one).isNotEmpty();
    }

    @Test
    public void event() {
        Post post = new Post();
        post.setTitle("event");
        PostPublishedEvent event = new PostPublishedEvent(post);

        applicationContext.publishEvent(event);
    }
}