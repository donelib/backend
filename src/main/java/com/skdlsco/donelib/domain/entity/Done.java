package com.skdlsco.donelib.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "done")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Done {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany
    @JoinTable(name = "done_tag", inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagList = new ArrayList<>();

    public void updateName(String name) {
        this.name = name;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public void addTag(Tag tag) {
        tagList.add(tag);
    }

    public void removeTag(Tag tag) {
        tagList.remove(tag);
    }

    @Builder
    public Done(String name) {
        this.name = name;
    }
}
