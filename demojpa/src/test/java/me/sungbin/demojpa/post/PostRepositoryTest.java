package me.sungbin.demojpa.post;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({PostRepositoryTestConfig.class})
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void crud() {
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