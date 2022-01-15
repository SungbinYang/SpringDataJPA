package me.sungbin.demospringdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        this.createComment(100, "spring data jpa");
        this.createComment(55, "HIBERNATE spring");

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));

        try (Stream<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest)) {
            Comment firstComment = comments.findFirst().get();
            assertEquals(firstComment.getLikeCount(), 100);
        }

    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setLikeCount(likeCount);
        newComment.setComment(comment);
        commentRepository.save(newComment);
    }
}