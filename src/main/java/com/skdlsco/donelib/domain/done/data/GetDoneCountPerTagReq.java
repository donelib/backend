package com.skdlsco.donelib.domain.done.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class GetDoneCountPerTagReq {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate month;
}
