package me.sungbin.commonweb.comment;

import lombok.Getter;
import lombok.Setter;
import me.sungbin.commonweb.post.Post;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
}
