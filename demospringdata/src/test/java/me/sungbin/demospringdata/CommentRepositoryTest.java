package me.sungbin.demospringdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    void crud_테스트() {
        Comment comment = new Comment();
        comment.setComment("Hello Comment");
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();
        assertEquals(all.size(), 1);
        assertNotNull(all); // 테스트 할 조차도 없는 코드 :: List 타입이기 때문에

        long count = commentRepository.count();
        assertEquals(count, 1);

        Optional<Comment> byId = commentRepository.findById(100L);
        assertThat(byId).isEmpty();
    }

    @Test
    void query_테스트() {

    }
}