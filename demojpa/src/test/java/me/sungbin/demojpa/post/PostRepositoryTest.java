package me.sungbin.demojpa.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void crud_테스트() {
        Post post = new Post();
        post.setTitle("hibernate");

        postRepository.save(post);

        postRepository.findMyPost();

        postRepository.delete(post);
        postRepository.flush();
    }
}