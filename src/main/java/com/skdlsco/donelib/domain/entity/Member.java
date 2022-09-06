package com.skdlsco.donelib.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonManagedReference
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tag> tagList = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Done> doneList = new ArrayList<>();

    public void addTag(Tag tag) {
        tagList.add(tag);
        tag.updateMember(this);
    }

    public void deleteTag(Tag tag) {
        tagList.remove(tag);
        tag.updateMember(null);
    }

    public void addDone(Done done) {
        done.updateMember(this);
        doneList.add(done);
    }

    public void deleteDone(Done done) {
        doneList.remove(done);
    }
}
