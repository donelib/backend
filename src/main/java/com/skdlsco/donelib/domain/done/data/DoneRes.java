package com.skdlsco.donelib.domain.done.data;

import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.tag.data.TagRes;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DoneRes {
    private Long id;
    private String name;
    private LocalDateTime doneAt;
    private List<TagRes> tags;

    static public DoneRes fromDone(Done done) {
        return DoneRes.builder()
                .id(done.getId())
                .name(done.getName())
                .doneAt(done.getDoneAt())
                .tags(done.getTagList().stream().map(TagRes::fromTag).toList())
                .build();
    }
}
