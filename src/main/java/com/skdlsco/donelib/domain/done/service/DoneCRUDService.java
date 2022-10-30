package com.skdlsco.donelib.domain.done.service;


import com.skdlsco.donelib.domain.done.data.DoneInfo;
import com.skdlsco.donelib.domain.entity.Done;

import java.time.LocalDateTime;
import java.util.List;

public interface DoneCRUDService {
    Done addDone(Long memberId, DoneInfo doneInfo);

    void deleteDone(Long memberId, Long doneId);

    List<Done> getDoneList(Long memberId, LocalDateTime from, LocalDateTime to);

    Done updateDone(Long memberId, Long doneId, DoneInfo doneInfo);
}
