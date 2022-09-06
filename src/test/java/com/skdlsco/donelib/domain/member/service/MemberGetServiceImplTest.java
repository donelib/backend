package com.skdlsco.donelib.domain.member.service;

import com.skdlsco.donelib.domain.test.DomainJpaTest;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.member.exception.MemberNotFound;
import com.skdlsco.donelib.domain.test.EntityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DomainJpaTest
class MemberGetServiceImplTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberGetServiceImpl memberGetService;

    @Test
    void getById() {
        //given
        Member member = EntityUtil.generate(em).member();

        //when
        Member findMember = memberGetService.getById(member.getId());

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void getById_notExistMember_failure() {
        assertThrows(MemberNotFound.class, () -> {
            memberGetService.getById(-1L);
        });
    }
}