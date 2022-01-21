package me.sungbin.commonweb.comment;

import me.sungbin.commonweb.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Test
    void getComment_테스트() {
        commentRepository.findWithPostById(1L);
        System.out.println("===============================");
        commentRepository.findById(1L);
    }
}