package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneInfo;
import com.skdlsco.donelib.domain.done.exception.DoneNotFound;
import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.test.EntityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static com.skdlsco.donelib.domain.test.EntityUtil.generate;
import static org.assertj.core.api.Assertions.*;

@DomainJpaTest
class DoneServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    DoneService doneService;

    @Test
    void addDone() {
        //given
        Member member = EntityUtil.generate(em).member();
        Tag tag = EntityUtil.generate(em).tag(member, "tag1", 1000);
        DoneInfo doneInfo = new DoneInfo("done1", LocalDateTime.now(), List.of(tag.getId()));

        //when
        Done done = doneService.addDone(member.getId(), doneInfo);

        //then
        assertThat(done.getMember()).isEqualTo(member);
        assertThat(done.getName()).isEqualTo("done1");
        assertThat(done.getTagList()).containsExactlyInAnyOrder(tag);
    }

    @Test
    void addDoneIgnoreAnotherTag() {
        //given
        Member member = EntityUtil.generate(em).member();
        Member another = EntityUtil.generate(em).member();
        Tag tag = EntityUtil.generate(em).tag(member, "tag1", 1000);
        Tag anotherTag = EntityUtil.generate(em).tag(another, "another", 1234);
        DoneInfo doneInfo = new DoneInfo("done1", LocalDateTime.now(), List.of(tag.getId(), another.getId()));

        //when
        Done done = doneService.addDone(member.getId(), doneInfo);
        //then
        assertThat(done.getMember()).isEqualTo(member);
        assertThat(done.getName()).isEqualTo("done1");
        assertThat(done.getTagList()).doesNotContain(anotherTag);
        assertThat(done.getTagList()).containsExactlyInAnyOrder(tag);
    }

    @Test
    void deleteDoneNotExist() {
        //given
        Member member = EntityUtil.generate(em).member();

        //when
        Exception exception = catchException(() -> {
            doneService.deleteDone(member.getId(), 123L);
        });

        //then
        assertThat(exception).isInstanceOf(DoneNotFound.class);
    }


    @Test
    void deleteDone() {
        //given
        Member member = EntityUtil.generate(em).member();
        Done done = EntityUtil.generate(em).done(member, "done1", null);
        em.clear();

        //when
        doneService.deleteDone(member.getId(), done.getId());
        em.flush();

        //then
        Done deleted = em.find(Done.class, done.getId());
        assertThat(deleted).isNull();
    }

    @Test
    void getDoneList() {
        //given
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        Member member = EntityUtil.generate(em).member();
        Done done1 = EntityUtil.generate(em).done(member, "done1", null);
        Done done2 = EntityUtil.generate(em).done(member, "done2", null);
        Done done3 = EntityUtil.generate(em).done(member, "done3", null);

        //when
        List<Done> doneList = doneService.getDoneList(member.getId(), from, LocalDateTime.now().plusDays(1));

        //then
        assertThat(doneList).containsExactlyInAnyOrder(done1, done2, done3);
    }

    @Test
    void updateDone() {
        //given
        Member member = generate(em).member();
        Tag tag1 = generate(em).tag(member, "inittag", 1000);
        Tag tag2 = generate(em).tag(member, "updatetag", 2000);
        Done done = EntityUtil.generate(em).done(member, "init", List.of(tag1));
        DoneInfo doneInfo = new DoneInfo("updated", LocalDateTime.now(), List.of(tag2.getId()));

        //when
        doneService.updateDone(member.getId(), done.getId(), doneInfo);

        Done updatedDone = em.find(Done.class, done.getId());
        //then
        assertThat(updatedDone.getId()).isEqualTo(done.getId());
        assertThat(updatedDone.getName()).isEqualTo("updated");
        assertThat(updatedDone.getTagList()).containsExactlyInAnyOrder(tag2);
    }

    @Test
    void updateDoneIgnoreAnotherTag() {
        //given
        Member member = generate(em).member();
        Tag tag1 = generate(em).tag(member, "inittag", 1000);
        Tag tag2 = generate(em).tag(member, "updatetag", 2000);
        Member another = EntityUtil.generate(em).member();
        Tag anotherTag = EntityUtil.generate(em).tag(another, "another", 1234);
        Done done = EntityUtil.generate(em).done(member, "init", List.of(tag1));
        DoneInfo doneInfo = new DoneInfo("updated", LocalDateTime.now(), List.of(tag2.getId(), anotherTag.getId()));

        //when
        doneService.updateDone(member.getId(), done.getId(), doneInfo);

        Done updatedDone = em.find(Done.class, done.getId());
        //then
        assertThat(updatedDone.getId()).isEqualTo(done.getId());
        assertThat(updatedDone.getName()).isEqualTo("updated");
        assertThat(updatedDone.getTagList()).containsExactlyInAnyOrder(tag2);
        assertThat(updatedDone.getTagList()).doesNotContain(anotherTag);
    }
}