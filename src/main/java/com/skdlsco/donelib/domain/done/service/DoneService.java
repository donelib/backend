package com.skdlsco.donelib.domain.done.service;

import com.skdlsco.donelib.domain.done.data.DoneInfo;
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

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoneService implements DoneCRUDService {
    final private DoneRepository doneRepository;
    final private TagRepository tagRepository;
    final private MemberGetService memberGetService;

    private Done getDoneById(Long tagId) {
        return doneRepository.findById(tagId).orElseThrow(() -> {
            return new BusinessException("Done not found", GlobalErrorCode.ENTITY_NOT_FOUND);
        });
    }

    @Override
    public Done addDone(Long memberId, DoneInfo doneInfo) {
        Member member = memberGetService.getById(memberId);
        Done newDone = Done.builder()
                .name(doneInfo.getName())
                .doneAt(doneInfo.getDoneAt())
                .build();
        List<Tag> tagList = tagRepository.findAllById(doneInfo.getTagList());

        newDone.setTagList(tagList);
        member.addDone(newDone);
        doneRepository.save(newDone);
        return newDone;
    }

    @Override
    public void deleteDone(Long memberId, Long doneId) {
        Member member = memberGetService.getById(memberId);
        Done done = getDoneById(doneId);

        member.deleteDone(done);
    }

    @Override
    public List<Done> getDoneList(Long memberId, LocalDateTime from, LocalDateTime to) {
        return doneRepository.findAllByMemberIdAndDoneAtBetween(memberId, from, to);
    }

    @Override
    public Done updateDone(Long memberId, Long doneId, DoneInfo doneInfo) {
        Member member = memberGetService.getById(memberId);
        Done done = getDoneById(doneId);

        if (!member.getDoneList().contains(done))
            throw new BusinessException(GlobalErrorCode.DONE_NOT_FOUND);

        List<Tag> tagList = tagRepository.findAllById(doneInfo.getTagList());
        done.updateName(doneInfo.getName());
        done.updateDoneAt(doneInfo.getDoneAt());
        done.setTagList(tagList);
        doneRepository.save(done);
        return done;
    }
}
