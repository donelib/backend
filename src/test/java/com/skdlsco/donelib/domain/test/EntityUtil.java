package com.skdlsco.donelib.domain.test;

import com.skdlsco.donelib.domain.entity.*;

import javax.persistence.EntityManager;
import java.util.List;

public class EntityUtil {
    public static EntityGenerator generate(EntityManager em) {
        return new EntityGenerator(em);
    }

    public static class EntityGenerator {
        private EntityManager em;

        EntityGenerator(EntityManager em) {
            this.em = em;
        }

        public Member member() {
            Member member = new Member();
            em.persist(member);
            return member;
        }

        public Tag tag(Member member, String name, int color) {
            Tag tag = new Tag(name, color);
            member.addTag(tag);
            em.persist(tag);
            return tag;
        }

        public Done done(Member member, String name, List<Tag> tagList) {
            Done done = new Done(name);
            member.addDone(done);
            if (tagList != null)
                done.setTagList(tagList);
            em.persist(done);
            return done;
        }

        public OAuthInfo oAuthInfo(Member member, OAuthType oAuthType, String oAuthId) {
            OAuthInfo oAuthInfo = new OAuthInfo(oAuthType, oAuthId);
            oAuthInfo.updateMember(member);
            em.persist(oAuthInfo);
            return oAuthInfo;
        }
    }
}
