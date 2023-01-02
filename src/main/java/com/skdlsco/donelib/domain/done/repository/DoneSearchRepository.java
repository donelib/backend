package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneSearchInfo;
import com.skdlsco.donelib.domain.entity.Done;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoneSearchRepository {
    List<Done> findBySearchInfo(Long memberId, DoneSearchInfo doneSearchInfo);
}
