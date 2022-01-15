package me.sungbin.demospringdata;

import org.springframework.data.repository.RepositoryDefinition;

//@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CommentRepository extends MyRepository<Comment, Long> {
}
