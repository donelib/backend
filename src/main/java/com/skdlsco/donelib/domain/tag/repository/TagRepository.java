package com.skdlsco.donelib.domain.tag.repository;

import com.skdlsco.donelib.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
