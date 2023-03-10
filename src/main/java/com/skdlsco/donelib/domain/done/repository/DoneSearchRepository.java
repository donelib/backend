package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneSearchInfo;
import com.skdlsco.donelib.domain.entity.Done;

import java.util.List;

public interface DoneSearchRepository {
    List<Done> findBySearchInfo(Long memberId, DoneSearchInfo doneSearchInfo);

}
