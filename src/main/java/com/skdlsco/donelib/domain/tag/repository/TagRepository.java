package com.skdlsco.donelib.domain.tag.repository;

import com.skdlsco.donelib.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByIdInAndMemberId(Collection<Long> ids, Long memberId);
}
