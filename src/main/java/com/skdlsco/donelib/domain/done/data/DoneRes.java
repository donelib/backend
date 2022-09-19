package com.skdlsco.donelib.domain.done.data;

import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.tag.data.TagRes;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DoneRes {
    private Long id;
    private String name;
    private List<TagRes> tagRes;

    static public DoneRes fromDone(Done done) {
        return DoneRes.builder()
                .id(done.getId())
                .name(done.getName())
                .tagRes(done.getTagList().stream().map(TagRes::fromTag).toList())
                .build();
    }
}
