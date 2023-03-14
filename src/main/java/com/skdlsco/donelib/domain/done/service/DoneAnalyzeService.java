package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.GetDoneCountPerDayReq;

import java.util.List;

public interface DoneAnalyzeService {
    List<DoneCountPerDay> countDonePerDay(Long memberId, GetDoneCountPerDayReq req);
}
