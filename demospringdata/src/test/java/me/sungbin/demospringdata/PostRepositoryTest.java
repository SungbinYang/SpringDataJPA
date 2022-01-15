package me.sungbin.demospringdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(value = false)
    void crud_테스트() {
        //given
        Post post = new Post();
        post.setTitle("hello spring boot common");
        assertNull(post.getId());

        // when
        Post newPost = postRepository.save(post);

        // Then
        assertNotNull(newPost.getId());

        // when
        List<Post> posts = postRepository.findAll();
        assertEquals(posts.size(), 1);
        assertThat(posts).contains(newPost);

        // when
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));

        // then
        assertEquals(page.getTotalElements(), 1);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getSize(), 10);
        assertEquals(page.getNumberOfElements(), 1);

        // when
        page = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));

        // then
        assertEquals(page.getTotalElements(), 1);
        assertEquals(page.getNumber(), 0);
        assertEquals(page.getSize(), 10);
        assertEquals(page.getNumberOfElements(), 1);

        // when
        long spring = postRepository.countByTitleContains("spring");

        // then
        assertEquals(spring, 1);
    }
}