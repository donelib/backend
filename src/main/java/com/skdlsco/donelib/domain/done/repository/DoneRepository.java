package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DoneRepository extends JpaRepository<Done, Long> {
    List<Done> findAllByMemberIdAndDoneAtBetween(Long memberId, LocalDateTime from, LocalDateTime to);
}
