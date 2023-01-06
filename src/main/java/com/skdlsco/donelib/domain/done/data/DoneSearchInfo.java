package com.skdlsco.donelib.domain.done.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DoneSearchInfo {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime doneAtTo;

    private List<Long> tagList;

    public boolean hasLeastOneCond() {
        return hasDateCond() || hasTagCond();
    }

    public boolean hasDateCond() {
        if (doneAtFrom == null || doneAtTo == null)
            return false;
        return doneAtFrom.isBefore(doneAtTo);
    }

    public boolean hasTagCond() {
        return tagList != null && !tagList.isEmpty();
    }
}
