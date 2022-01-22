package me.sungbin.commonweb.comment;

import me.sungbin.commonweb.post.Post;
import me.sungbin.commonweb.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;

import static me.sungbin.commonweb.comment.CommentSpecs.isBest;
import static me.sungbin.commonweb.comment.CommentSpecs.isGood;

@SpringBootTest
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
        System.out.println("===============================");
        commentRepository.findByPost_Id(1L, CommentSummary.class);
    }

    @Test
    void projection_테스트() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);

        Comment comment = new Comment();
        comment.setComment("Spring Data JPA");
        comment.setPost(savedPost);
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        commentRepository.findByPost_Id(savedPost.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("========================");
            System.out.println(c.getComment());
        });
    }

    @Test
    void specification_테스트() {
        commentRepository.findAll(isBest().or(isGood()), PageRequest.of(0, 10));
    }

    @Test
    void query_by_example_테스트() {
        Comment prove = new Comment();
        prove.setBest(true); // prove

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up", "down");

        Example<Comment> example = Example.of(prove, exampleMatcher);

        commentRepository.findAll(example);
    }
}