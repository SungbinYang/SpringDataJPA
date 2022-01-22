package me.sungbin.commonweb.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentSummary {

    private String comment;

    private int up;

    private int down;

    public String getVotes() {
        return this.up + " " + this.down;
    }
}
