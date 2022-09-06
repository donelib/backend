package com.skdlsco.donelib.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oauth_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "oauth_id")
    private String oAuthId;

    @Column(name = "oauth_type")
    @Enumerated(EnumType.STRING)
    private OAuthType oAuthType;

    @ManyToOne(targetEntity = Member.class)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateMember(Member member) {
        this.member = member;
    }

    @Builder
    public OAuthInfo(OAuthType oAuthType, String oAuthId) {
        this.oAuthId = oAuthId;
        this.oAuthType = oAuthType;
    }
}
