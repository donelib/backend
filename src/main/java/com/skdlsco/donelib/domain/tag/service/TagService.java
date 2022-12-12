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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    @Override
    public Tag addTag(Long memberId, TagInfo tagInfo) {
        Tag newTag = tagInfo.toTag();

        Member member = memberGetService.getById(memberId);
        member.addTag(newTag);
        return newTag;
    }

    @Transactional
    @Override
    public void deleteTag(Long memberId, Long tagId) {
        Member member = memberGetService.getById(memberId);
        Optional<Tag> tag =  tagRepository.findById(tagId);

        // 존재 하지 않는 경우 무시한다.
        if (tag.isPresent())
            member.deleteTag(tag.get());
    }

    @Override
    public List<Tag> getTagList(Long memberId) {
        Member member = memberGetService.getById(memberId);

        return member.getTagList();
    }

    @Transactional
    @Override
    public Tag updateTag(Long memberId, Long tagId, TagInfo tagInfo) {
        Tag tag = getTagById(tagId);

        if (!tag.getMember().getId().equals(memberId))
            throw new BusinessException(GlobalErrorCode.TAG_NOT_FOUND);

        tag.updateColor(tagInfo.getColor());
        tag.updateName(tagInfo.getName());
        return tag;
    }
}
