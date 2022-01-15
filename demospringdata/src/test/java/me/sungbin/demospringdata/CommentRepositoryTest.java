package me.sungbin.demospringdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void query_테스트() throws ExecutionException, InterruptedException {
        this.createComment(100, "spring data jpa");
        this.createComment(55, "HIBERNATE spring");
        commentRepository.flush();

        List<Comment> all = commentRepository.findAll();
        assertEquals(all.size(), 2);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount"));

        ListenableFuture<List<Comment>> comments = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);
        System.out.println("=====================");
        System.out.println("is Done? " + comments.isDone());

        comments.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println(ex);
            }

            @Override
            public void onSuccess(@Nullable List<Comment> result) {
                System.out.println("==================");
                System.out.println(result.size());
                result.forEach(System.out::println);
            }
        });
        Thread.sleep(5000L);
    }

    private void createComment(int likeCount, String comment) {
        Comment newComment = new Comment();
        newComment.setLikeCount(likeCount);
        newComment.setComment(comment);
        commentRepository.save(newComment);
    }
}