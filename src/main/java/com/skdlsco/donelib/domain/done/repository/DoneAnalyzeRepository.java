package com.skdlsco.donelib.domain.done.repository;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.DoneCountPerTag;

import java.time.LocalDateTime;
import java.util.List;

public interface DoneAnalyzeRepository {

    List<DoneCountPerDay> countDonePerDayInRange(Long memberId, LocalDateTime from, LocalDateTime to);

    List<DoneCountPerTag> countDonePerTagInRange(Long memberId, LocalDateTime from, LocalDateTime to);
}
