package com.skdlsco.donelib.domain.tag.service;

import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.tag.data.TagInfo;
import com.skdlsco.donelib.domain.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.skdlsco.donelib.domain.test.EntityUtil.generate;
import static org.assertj.core.api.Assertions.assertThat;

@DomainJpaTest
class TagServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TagService tagService;

    @Test
    void addTag() {
        //given
        Member member = generate(em).member();
        TagInfo tagInfo = new TagInfo("tagname", 1000);

        //when
        Tag tag = tagService.addTag(member.getId(), tagInfo);
        em.flush();

        //then
        assertThat(member.getTagList().size()).isEqualTo(1);
        assertThat(tag.getName()).isEqualTo("tagname");
        assertThat(tag.getColor()).isEqualTo(1000);
        assertThat(tag.getId()).isNotNull();
    }

    @Test
    void deleteTag() {
        //given
        Member member = generate(em).member();
        Tag tag = generate(em).tag(member, "tagname", 1000);
        Long tagId = tag.getId();
        em.clear();

        //then
        tagService.deleteTag(member.getId(), tagId);
        em.flush();

        //when
        Tag deleted = em.find(Tag.class, tagId);
        assertThat(deleted).isNull();
    }

    @Test
    void getTagList() {
        //given
        Member member = generate(em).member();
        Tag tag1 = generate(em).tag(member, "tagname1", 1000);
        Tag tag2 = generate(em).tag(member, "tagname2", 2000);

        //then
        List<Tag> tagList = tagService.getTagList(member.getId());

        //when
        assertThat(tagList).containsExactlyInAnyOrder(tag1, tag2);
    }

    @Test
    void updateTag() {
        //given
        Member member = generate(em).member();
        Tag tag = generate(em).tag(member, "tagname", 1000);
        TagInfo tagInfo = new TagInfo("tagname_changed", 2000);

        em.clear();
        //then
        tagService.updateTag(member.getId(), tag.getId(), tagInfo);
        em.flush();
        Tag updatedTag = em.find(Tag.class, tag.getId());
        //when
        assertThat(updatedTag.getId()).isEqualTo(tag.getId());
        assertThat(updatedTag.getName()).isEqualTo("tagname_changed");
        assertThat(updatedTag.getColor()).isEqualTo(2000);
    }
}