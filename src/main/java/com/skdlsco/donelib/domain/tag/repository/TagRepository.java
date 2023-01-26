package com.skdlsco.donelib.domain.tag.repository;

import com.skdlsco.donelib.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByIdInAndMemberId(Collection<Long> ids, Long memberId);
}
