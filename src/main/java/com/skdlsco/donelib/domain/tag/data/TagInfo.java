package com.skdlsco.donelib.domain.tag.data;

import com.skdlsco.donelib.domain.entity.Tag;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TagInfo {
    private String name;
    private int color;

    public Tag toTag() {
        return Tag.builder()
                .name(name)
                .color(color)
                .build();
    }
}
