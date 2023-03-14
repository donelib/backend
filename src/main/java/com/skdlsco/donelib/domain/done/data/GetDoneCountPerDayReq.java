package com.skdlsco.donelib.domain.done.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class GetDoneCountPerDayReq {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtTo;

    public boolean isOver365Days() {
        long daysBetween = ChronoUnit.DAYS.between(doneAtFrom, doneAtTo);
        return daysBetween > 366;
    }
}
