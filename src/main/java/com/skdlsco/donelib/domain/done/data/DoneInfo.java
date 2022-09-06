package com.skdlsco.donelib.domain.done.data;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DoneInfo {
    private String name;
    private List<Long> tagList;
}
