package com.skdlsco.donelib.domain.tag.data;

import com.skdlsco.donelib.domain.entity.Tag;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TagRes {
    private Long id;
    private String name;

    private int color;

    static public TagRes fromTag(Tag tag) {
        return TagRes.builder()
                .id(tag.getId())
                .name(tag.getName())
                .color(tag.getColor())
                .build();
    }
}
