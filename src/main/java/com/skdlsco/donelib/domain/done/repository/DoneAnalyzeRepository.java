package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;

import java.time.LocalDateTime;
import java.util.List;

public interface DoneAnalyzeRepository {

    List<DoneCountPerDay> countDonePerDayInRange(Long memberId, LocalDateTime from, LocalDateTime to);
}
