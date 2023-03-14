package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.GetDoneCountPerDayReq;
import com.skdlsco.donelib.domain.done.repository.DoneAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoneAnalyzeServiceImpl implements DoneAnalyzeService {
    final private DoneAnalyzeRepository doneAnalyzeRepository;

    @Override
    public List<DoneCountPerDay> countDonePerDay(Long memberId, GetDoneCountPerDayReq req) {
        return doneAnalyzeRepository.countDonePerDayInRange(memberId, req.getDoneAtFrom(), req.getDoneAtTo());
    }
}