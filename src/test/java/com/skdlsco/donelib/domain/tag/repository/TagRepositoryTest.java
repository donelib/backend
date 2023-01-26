package com.skdlsco.donelib.domain.tag.repository;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.test.EntityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DomainJpaTest
class TagRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TagRepository tagRepository;

    @Test
    void findAllByIdInAndMemberId() {
        Member otherMember = EntityUtil.generate(em).member();
        Tag otherTag = EntityUtil.generate(em).tag(otherMember, "otherTag", 1000);
        Member member = EntityUtil.generate(em).member();
        Tag tag1 = EntityUtil.generate(em).tag(member, "testTagName1", 2000);
        Tag tag2 = EntityUtil.generate(em).tag(member, "testTagName2", 3000);

        List<Long> idList = List.of(tag1.getId(), tag2.getId());
        List<Tag> findTagList = tagRepository.findAllByIdInAndMemberId(idList, member.getId());

        assertThat(findTagList).doesNotContain(otherTag);
        assertThat(findTagList).containsExactlyInAnyOrder(tag1, tag2);
    }
}