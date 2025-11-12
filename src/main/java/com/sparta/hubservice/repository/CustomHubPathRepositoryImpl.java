package com.sparta.hubservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hubservice.domain.HubPath;
import com.sparta.hubservice.domain.QHubPath;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomHubPathRepositoryImpl implements CustomHubPathRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<HubPath> searchHubPaths(String searchParam, Pageable pageable) {
        QHubPath hubPath = QHubPath.hubPath;

        BooleanBuilder condition = new BooleanBuilder();
        condition.and(hubPath.deletedAt.isNull());

        if (searchParam != null && !searchParam.isBlank()) {
            condition.and(
                    hubPath.startHub.hubName.containsIgnoreCase(searchParam)
                            .or(hubPath.endHub.hubName.containsIgnoreCase(searchParam))
            );
        }

        List<HubPath> results = queryFactory
                .selectFrom(hubPath)
                .where(condition)
                .orderBy(hubPath.createdAt.desc(), hubPath.updatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(hubPath.count())
                .from(hubPath)
                .where(condition)
                .fetchOne();

        return PageableExecutionUtils.getPage(results, pageable, () -> total);
    }
}
