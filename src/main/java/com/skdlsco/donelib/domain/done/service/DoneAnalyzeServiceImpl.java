package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.DoneCountPerTag;
import com.skdlsco.donelib.domain.done.data.GetDoneCountPerDayReq;
import com.skdlsco.donelib.domain.done.data.GetDoneCountPerTagReq;
import com.skdlsco.donelib.domain.done.repository.DoneAnalyzeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime dayFirstOfMonth(LocalDate date) {
        return date.atStartOfDay().minusDays(date.getDayOfMonth() - 1);
    }

    @Override
    public List<DoneCountPerTag> countDonePerTag(Long memberId, GetDoneCountPerTagReq req) {
        LocalDateTime from = dayFirstOfMonth(req.getMonth());
        LocalDateTime to = from.plusMonths(1);

        return doneAnalyzeRepository.countDonePerTagInRange(memberId, from, to);
    }
}
