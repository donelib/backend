package com.skdlsco.donelib.domain.tag.controller;

import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.tag.data.TagInfo;
import com.skdlsco.donelib.domain.tag.data.TagRes;
import com.skdlsco.donelib.domain.tag.service.TagCRUDService;
import com.skdlsco.donelib.global.auth.LoginMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "tag")
@RequiredArgsConstructor
public class TagController {
    final private TagCRUDService tagCRUDService;

    @PostMapping()
    public TagRes addTag(@LoginMember Member loginMember, @RequestBody TagInfo tagInfo) {
        Tag tag = tagCRUDService.addTag(loginMember.getId(), tagInfo);

        return TagRes.fromTag(tag);
    }

    @GetMapping(produces = "application/json")
    public List<TagRes> getTagList(@LoginMember Member loginMember) {
        return tagCRUDService.getTagList(loginMember.getId()).stream().map(TagRes::fromTag).toList();
    }

    @DeleteMapping("{id}")
    public void deleteTag(@LoginMember Member loginMember, @PathVariable("id") Long tagId) {
        tagCRUDService.deleteTag(loginMember.getId(), tagId);
    }

    @PutMapping("{id}")
    public TagRes updateTag(@LoginMember Member loginMember, @PathVariable("id") Long tagId, @RequestBody TagInfo tagInfo) {
        Tag tag = tagCRUDService.updateTag(loginMember.getId(), tagId, tagInfo);

        return TagRes.fromTag(tag);
    }
}
