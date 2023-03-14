package com.skdlsco.donelib.domain.done.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skdlsco.donelib.domain.done.data.DoneCountPerDay;
import com.skdlsco.donelib.domain.done.data.QDoneCountPerDay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.skdlsco.donelib.domain.entity.QDone.done;

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
                .where(memberIdEq(memberId), done.doneAt.between(from, to))
                .groupBy(doneAtDate)
                .orderBy(doneAtDate.desc())
                .fetch();
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return done.member.id.eq(memberId);
    }

}
