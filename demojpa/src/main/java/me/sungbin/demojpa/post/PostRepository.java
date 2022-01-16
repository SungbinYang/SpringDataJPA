package me.sungbin.demojpa.post;

import me.sungbin.demojpa.MyRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends MyRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
}
