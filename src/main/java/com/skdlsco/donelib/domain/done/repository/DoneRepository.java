package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.entity.Done;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoneRepository extends JpaRepository<Done, Long> {
}
