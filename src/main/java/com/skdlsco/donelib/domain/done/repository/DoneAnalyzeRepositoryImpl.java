package com.skdlsco.donelib.domain.done.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.DoneCountPerTag;
import com.skdlsco.donelib.domain.done.data.QDoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.QDoneCountPerTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.skdlsco.donelib.domain.entity.QDone.done;
import static com.skdlsco.donelib.domain.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DoneAnalyzeRepositoryImpl implements DoneAnalyzeRepository {

    final private JPAQueryFactory queryFactory;

    @Override
    public List<DoneCountPerDay> countDonePerDayInRange(Long memberId, LocalDateTime from, LocalDateTime to) {
        StringExpression doneAtDate =
                Expressions.stringTemplate("cast(cast({0} as date) as string)", done.doneAt);
        return queryFactory.select(
                        new QDoneCountPerDay(doneAtDate,
                                done.count()))
                .from(done)
                .where(memberIdEq(memberId), doneAtBetween(from, to))
                .groupBy(doneAtDate)
                .orderBy(doneAtDate.desc())
                .fetch();
    }

    @Override
    public List<DoneCountPerTag> countDonePerTagInRange(Long memberId, LocalDateTime from, LocalDateTime to) {
        return queryFactory.select(
                        new QDoneCountPerTag(
                                tag.id,
                                tag.name,
                                tag.color,
                                done.count()))
                .from(done)
                .innerJoin(done.tagList, tag)
                .where(memberIdEq(memberId), doneAtBetween(from, to))
                .groupBy(tag.id)
                .fetch();
    }


    private BooleanExpression doneAtBetween(LocalDateTime from, LocalDateTime to) {
        return done.doneAt.between(from, to);
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return done.member.id.eq(memberId);
    }
}
