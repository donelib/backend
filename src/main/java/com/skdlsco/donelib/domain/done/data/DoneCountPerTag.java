package com.skdlsco.donelib.domain.done.data;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class DoneCountPerTag {
    private Long id;
    private String name;

    private int color;
    private Long doneCount;

    @QueryProjection
    public DoneCountPerTag(Long id, String name, int color, Long doneCount) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.doneCount = doneCount;
    }
}
