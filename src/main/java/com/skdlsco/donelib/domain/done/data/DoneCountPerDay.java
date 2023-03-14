package com.skdlsco.donelib.domain.done.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DoneCountPerDay {
    final private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private LocalDateTime dateTime;
    private Long doneCount;

    @QueryProjection
    public DoneCountPerDay(String dateTime, Long doneCount) {
        this.dateTime = LocalDate.parse(dateTime, FORMATTER).atStartOfDay();
        this.doneCount = doneCount;
    }
}
