package me.sungbin.demospringdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Comment {

    @Id @GeneratedValue @ToString.Exclude
    private Long id;

    private String comment;

    @ManyToOne @ToString.Exclude
    private Post post;

    @ToString.Exclude
    private Date created;

    @ToString.Exclude
    private Integer likeCount = 0;
}
