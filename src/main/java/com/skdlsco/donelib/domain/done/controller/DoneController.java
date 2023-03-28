package com.skdlsco.donelib.domain.done.controller;

import com.skdlsco.donelib.domain.done.data.*;
import com.skdlsco.donelib.domain.done.service.DoneAnalyzeService;
import com.skdlsco.donelib.domain.done.service.DoneCRUDService;
import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.global.auth.LoginMember;
import com.skdlsco.donelib.global.error.ErrorDetail;
import com.skdlsco.donelib.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "done")
@RequiredArgsConstructor
public class DoneController {
    final private DoneCRUDService doneCRUDService;
    final private DoneAnalyzeService doneAnalyzeService;

    @PostMapping()
    public DoneRes addDone(@LoginMember Member loginMember, @Valid @RequestBody DoneInfo doneInfo) {
        Done done = doneCRUDService.addDone(loginMember.getId(), doneInfo);

        return DoneRes.fromDone(done);
    }

    @DeleteMapping("{doneId}")
    public void deleteDoneById(@LoginMember Member loginMember, @PathVariable("doneId") Long doneId) {
        doneCRUDService.deleteDone(loginMember.getId(), doneId);
    }

    @PutMapping("{doneId}")
    public DoneRes updateDoneById(@LoginMember Member loginMember, @PathVariable("doneId") Long doneId, @Valid @RequestBody DoneInfo doneInfo) {
        Done done = doneCRUDService.updateDone(loginMember.getId(), doneId, doneInfo);

        return DoneRes.fromDone(done);
    }

    @GetMapping()
    public List<DoneRes> getDoneList(@LoginMember Member loginMember, @Valid @ModelAttribute DoneSearchInfo req) {
        if (!req.hasLeastOneCond())
            throw new BadRequestException(ErrorDetail.of("", "하나 이상의 검색 조건이 필요합니다."));
        List<Done> doneList = doneCRUDService.getDoneList(loginMember.getId(), req);

        return doneList.stream().map(DoneRes::fromDone).toList();
    }

    @GetMapping("analyze/done-count-per-day")
    public List<DoneCountPerDay> getDoneCountPerDay(@LoginMember Member loginMember, @Valid @ModelAttribute GetDoneCountPerDayReq req) {
        if (req.isOver1Year())
            throw new BadRequestException(ErrorDetail.of("", "요청 날짜 범위는 최대 1년까지입니다."));
        return doneAnalyzeService.countDonePerDay(loginMember.getId(), req);
    }

    @GetMapping("analyze/done-count-per-tag")
    public List<DoneCountPerTag> getDoneCountPerTag(@LoginMember Member loginMember, @Valid @ModelAttribute GetDoneCountPerTagReq req) {
        return doneAnalyzeService.countDonePerTag(loginMember.getId(), req);
    }
}
