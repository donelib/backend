package com.skdlsco.donelib.domain.done.controller;

import com.skdlsco.donelib.domain.done.data.DoneInfo;
import com.skdlsco.donelib.domain.done.data.DoneRes;
import com.skdlsco.donelib.domain.done.service.DoneCRUDService;
import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.global.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "done")
@RequiredArgsConstructor
public class DoneController {
    final private DoneCRUDService doneCRUDService;

    @PostMapping()
    public DoneRes addDone(@LoginMember Member loginMember, @RequestBody DoneInfo doneInfo) {
        Done done = doneCRUDService.addDone(loginMember.getId(), doneInfo);

        return DoneRes.fromDone(done);
    }

    @DeleteMapping("{doneId}")
    public void deleteDoneById(@LoginMember Member loginMember, @PathVariable("doneId") Long doneId) {
        doneCRUDService.deleteDone(loginMember.getId(), doneId);
    }

    @PutMapping("{doneId}")
    public DoneRes updateDoneById(@LoginMember Member loginMember, @PathVariable("doneId") Long doneId, @RequestBody DoneInfo doneInfo) {
        Done done = doneCRUDService.updateDone(loginMember.getId(), doneId, doneInfo);

        return DoneRes.fromDone(done);
    }

    @GetMapping()
    public List<DoneRes> getDoneList(@LoginMember Member loginMember) {
        List<Done> doneList = doneCRUDService.getDoneList(loginMember.getId());

        return doneList.stream().map(DoneRes::fromDone).toList();
    }
}
