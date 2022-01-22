package me.sungbin.commonweb.comment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"post"})
    Optional<Comment> findWithPostById(Long id);

    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
