package me.sungbin.demospringdata;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    public void addStudies(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    public void removeStudies(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }
}
