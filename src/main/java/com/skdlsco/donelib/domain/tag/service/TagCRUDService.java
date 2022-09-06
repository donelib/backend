package com.skdlsco.donelib.domain.tag.service;

import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.tag.data.TagInfo;

import java.util.List;

public interface TagCRUDService {

    Tag addTag(Long memberId, TagInfo tagInfo);

    void deleteTag(Long memberId, Long tagId);

    List<Tag> getTagList(Long memberId);

    Tag updateTag(Long memberId, Long tagId, TagInfo tagInfo);
}
