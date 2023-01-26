package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.entity.Done;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneRepository extends JpaRepository<Done, Long> {

}
