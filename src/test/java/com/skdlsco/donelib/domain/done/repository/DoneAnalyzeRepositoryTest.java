package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.DoneCountPerTag;
import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.test.EntityUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DomainJpaTest
class DoneAnalyzeRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    DoneAnalyzeRepository doneAnalyzeRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void countDonePerDayInRange() {
        // given
        LocalDateTime from = LocalDateTime.of(2022, 2, 1, 0, 0, 0);
        LocalDateTime to = LocalDateTime.of(2022, 4, 1, 0, 0, 0);

        Member member = EntityUtil.generate(em).member();

        LocalDateTime date1 = from.plusDays(3);
        LocalDateTime date2 = from.plusDays(5);

        Done done1 = EntityUtil.generate(em).done(member, "doneName", List.of(), date1);
        Done done2 = EntityUtil.generate(em).done(member, "doneName2", List.of(), date2.plusMinutes(6));
        Done done3 = EntityUtil.generate(em).done(member, "doneName3", List.of(), date2.plusHours(1));
        Done lessDate = EntityUtil.generate(em).done(member, "lessDate", List.of(), from.minusDays(10));
        Done overDate = EntityUtil.generate(em).done(member, "overDate", List.of(), to.plusDays(10));

        Member otherMember = EntityUtil.generate(em).member();
        Done otherDone = EntityUtil.generate(em).done(otherMember, "otherDone", List.of(), date2);

        // when
        List<DoneCountPerDay> countList = doneAnalyzeRepository.countDonePerDayInRange(member.getId(), from, to);

        //then
        assertThat(countList)
                .extracting("doneCount", Long.class)
                .containsExactlyInAnyOrder(1L, 2L);
        assertThat(countList)
                .extracting("dateTime", LocalDateTime.class)
                .containsExactlyInAnyOrder(date1, date2);
    }

    @Test
    void countDonePerTagInRange() {
        // given
        LocalDateTime month = LocalDateTime.of(2022, 2, 1, 0, 0, 0);

        Member member = EntityUtil.generate(em).member();

        LocalDateTime date1 = month.plusDays(3);
        Tag tag1 = EntityUtil.generate(em).tag(member, "tag1", 1000);
        Tag tag2 = EntityUtil.generate(em).tag(member, "tag2", 2000);
        Tag tag3 = EntityUtil.generate(em).tag(member, "tag3", 3000);
        Done done1 = EntityUtil.generate(em).done(member, "doneName1", List.of(tag1), date1);
        Done done2 = EntityUtil.generate(em).done(member, "doneName2", List.of(tag1, tag2), month);
        Done done3 = EntityUtil.generate(em).done(member, "doneName3", List.of(), date1);
        Done lessDate = EntityUtil.generate(em).done(member, "lessDate", List.of(tag1), month.minusDays(10));

        Member otherMember = EntityUtil.generate(em).member();
        Done otherDone = EntityUtil.generate(em).done(otherMember, "otherDone", List.of(), date1);

        // when
        List<DoneCountPerTag> countList = doneAnalyzeRepository.countDonePerTagInRange(member.getId(), month, month.plusMonths(1));

        //then
        assertThat(countList)
                .extracting("name", String.class)
                .doesNotContain(tag3.getName())
                .containsExactlyInAnyOrder(tag1.getName(), tag2.getName());
        assertThat(countList)
                .extracting("doneCount", Long.class)
                .containsExactlyInAnyOrder(1L, 2L);
    }
}