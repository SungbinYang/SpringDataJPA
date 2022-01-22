package me.sungbin.commonweb.comment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment>, QueryByExampleExecutor<Comment> {

    @EntityGraph(attributePaths = {"post"})
    Optional<Comment> findWithPostById(Long id);

    @Transactional(readOnly = true)
    <T> List<T> findByPost_Id(Long id, Class<T> type);
}
