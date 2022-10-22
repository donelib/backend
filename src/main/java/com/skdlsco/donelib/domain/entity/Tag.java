package com.skdlsco.donelib.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.skdlsco.donelib.domain.entity.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateColor(int color) {
        this.color = color;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public Tag(String name, int color) {
        this.name = name;
        this.color = color;
    }
}
