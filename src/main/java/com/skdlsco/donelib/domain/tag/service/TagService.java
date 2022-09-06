package com.skdlsco.donelib.domain.tag.service;


import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.member.repository.MemberRepository;
import com.skdlsco.donelib.domain.member.service.MemberGetService;
import com.skdlsco.donelib.domain.tag.data.TagInfo;
import com.skdlsco.donelib.domain.tag.repository.TagRepository;
import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService implements TagCRUDService {

    final private TagRepository tagRepository;
    final private MemberGetService memberGetService;

    private Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> {
            return new BusinessException(GlobalErrorCode.TAG_NOT_FOUND);
        });
    }

    @Override
    public Tag addTag(Long memberId, TagInfo tagInfo) {
        Tag newTag = tagInfo.toTag();

        Member member = memberGetService.getById(memberId);
        member.addTag(newTag);
        tagRepository.save(newTag);
        return newTag;
    }

    @Override
    public void deleteTag(Long memberId, Long tagId) {
        Member member = memberGetService.getById(memberId);
        Tag tag = getTagById(tagId);

        member.deleteTag(tag);
    }

    @Override
    public List<Tag> getTagList(Long memberId) {
        Member member = memberGetService.getById(memberId);

        return member.getTagList();
    }

    @Override
    public Tag updateTag(Long memberId, Long tagId, TagInfo tagInfo) {
        Member member = memberGetService.getById(memberId);
        Tag tag = getTagById(tagId);

        if (!member.getTagList().contains(tag))
            throw new BusinessException(GlobalErrorCode.TAG_NOT_FOUND);

        tag.updateColor(tagInfo.getColor());
        tag.updateName(tagInfo.getName());
        tagRepository.save(tag);
        return tag;
    }
}
