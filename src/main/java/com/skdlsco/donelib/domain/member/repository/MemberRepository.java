package com.skdlsco.donelib.domain.member.repository;

import com.skdlsco.donelib.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
