package me.sungbin.commonweb.comment;

import org.springframework.data.jpa.domain.Specification;

public class CommentSpecs {

    public static Specification<Comment> isBest() {
        return (root, query, builder) -> builder.isTrue(root.get(me.sungbin.commonweb.comment.Comment_.best));
    }

    public static Specification<Comment> isGood() {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(me.sungbin.commonweb.comment.Comment_.up), 10);
    }
}
