package com.skdlsco.donelib.domain.done.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skdlsco.donelib.domain.done.data.DoneSearchInfo;
import com.skdlsco.donelib.domain.entity.Done;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.skdlsco.donelib.domain.entity.QDone.done;
import static com.skdlsco.donelib.domain.entity.QTag.tag;

@RequiredArgsConstructor
@Repository
public class DoneSearchRepositoryImpl implements DoneSearchRepository {

    final private JPAQueryFactory queryFactory;

    @Override
    public List<Done> findBySearchInfo(Long memberId, DoneSearchInfo doneSearchInfo) {
        List<Done> findDone = queryFactory
                .select(done)
                .from(done)
                .leftJoin(done.tagList, tag)
                    .fetchJoin()
                .where(doneAtBetween(doneSearchInfo.getDoneAtFrom(), doneSearchInfo.getDoneAtTo()),
                        tagIdIn(doneSearchInfo.getTagList()))
                .fetch();
        return findDone;
    }

    private BooleanExpression tagIdIn(List<Long> tagIdList) {
        if (tagIdList == null)
            return null;
        return tag.id.in(tagIdList);
    }

    private BooleanExpression doneAtBetween(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null)
            return null;
        return done.doneAt.between(from, to);
    }
}
