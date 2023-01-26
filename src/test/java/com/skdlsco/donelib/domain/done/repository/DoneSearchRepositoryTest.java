package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneSearchInfo;
import com.skdlsco.donelib.domain.entity.Done;
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
class DoneSearchRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    DoneSearchRepository doneSearchRepository;

    @Test
    void findBySearchInfo() {
        // given
        Member member = EntityUtil.generate(em).member();
        Tag tag1 = EntityUtil.generate(em).tag(member, "testTagName1", 2000);
        Tag tag2 = EntityUtil.generate(em).tag(member, "testTagName2", 3000);
        Done done1 = EntityUtil.generate(em).done(member, "doneName", List.of(tag1, tag2));
        Done done2 = EntityUtil.generate(em).done(member, "doneName2", List.of(tag2));
        Done done3 = EntityUtil.generate(em).done(member, "doneName3", List.of());

        Member otherMember = EntityUtil.generate(em).member();
        Done otherDone = EntityUtil.generate(em).done(otherMember, "otherDone", List.of());

        // when
        DoneSearchInfo searchInfo = DoneSearchInfo.builder()
                .tagList(List.of(tag2.getId()))
                .build();

        List<Done> findDone = doneSearchRepository.findBySearchInfo(member.getId(), searchInfo);

        // then
        assertThat(findDone).contains(done1, done2);
        assertThat(findDone).doesNotContain(otherDone, done3);
    }
}