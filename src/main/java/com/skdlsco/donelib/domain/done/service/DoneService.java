package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneInfo;
import com.skdlsco.donelib.domain.done.data.DoneSearchInfo;
import com.skdlsco.donelib.domain.done.exception.DoneNotFound;
import com.skdlsco.donelib.domain.done.repository.DoneRepository;
import com.skdlsco.donelib.domain.entity.Member;
import com.skdlsco.donelib.domain.entity.Done;
import com.skdlsco.donelib.domain.entity.Tag;
import com.skdlsco.donelib.domain.member.service.MemberGetService;
import com.skdlsco.donelib.domain.tag.repository.TagRepository;
import com.skdlsco.donelib.global.error.code.GlobalErrorCode;
import com.skdlsco.donelib.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoneService implements DoneCRUDService {
    final private DoneRepository doneRepository;
    final private TagRepository tagRepository;
    final private MemberGetService memberGetService;

    private Done getDoneById(Long tagId, Long memberId) {
        Done done = doneRepository.findById(tagId).orElseThrow(DoneNotFound::new);
        if (!done.getMember().getId().equals(memberId))
            throw new DoneNotFound();
        return done;
    }

    @Transactional
    @Override
    public Done addDone(Long memberId, DoneInfo doneInfo) {
        Member member = memberGetService.getById(memberId);
        Done newDone = Done.builder()
                .name(doneInfo.getName())
                .doneAt(doneInfo.getDoneAt())
                .build();
        List<Tag> tagList = tagRepository.findAllByIdInAndMemberId(doneInfo.getTagList(), memberId);

        newDone.setTagList(tagList);
        member.addDone(newDone);
        return newDone;
    }

    @Transactional
    @Override
    public void deleteDone(Long memberId, Long doneId) {
        Member member = memberGetService.getById(memberId);
        Done done = getDoneById(doneId, memberId);

        member.deleteDone(done);
    }

    @Override
    public List<Done> getDoneList(Long memberId, DoneSearchInfo doneSearchInfo) {
        return doneRepository.findBySearchInfo(memberId, doneSearchInfo);
    }

    @Transactional
    @Override
    public Done updateDone(Long memberId, Long doneId, DoneInfo doneInfo) {
        Done done = getDoneById(doneId, memberId);
        List<Tag> tagList = tagRepository.findAllByIdInAndMemberId(doneInfo.getTagList(), memberId);

        done.updateName(doneInfo.getName());
        done.updateDoneAt(doneInfo.getDoneAt());
        done.setTagList(tagList);
        return done;
    }
}
