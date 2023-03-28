package com.skdlsco.donelib.domain.done.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Builder
public class GetDoneCountPerDayReq {
    @NotNull

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtFrom;
    @NotNull

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtTo;

    public boolean isOver1Year() {
        long daysBetween = ChronoUnit.DAYS.between(doneAtFrom, doneAtTo);
        return daysBetween > 366;
    }
}
