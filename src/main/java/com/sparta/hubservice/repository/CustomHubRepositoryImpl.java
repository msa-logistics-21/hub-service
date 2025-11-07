package com.sparta.hubservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.hubservice.domain.Hub;
import com.sparta.hubservice.domain.QHub;
import com.sparta.hubservice.dto.response.GetHubResDto;
import com.sparta.hubservice.dto.response.QGetHubResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class CustomHubRepositoryImpl implements CustomHubRepository {
    private final JPAQueryFactory query;

    private static final Set<String> ALLOWED_SORT_PROPERTIES = Set.of(
            "createdAt", "updatedAt", "hubName",  "hubAddress", "longitude", "latitude"
    );

    QHub qHub = QHub.hub;

    @Override
    public Page<GetHubResDto> findHubPage(String searchParam, Pageable pageable) {

        int pageSize = pageable.getPageSize();
        List<Integer> allowedPageSizes = Arrays.asList(10, 30, 50);
        if (!allowedPageSizes.contains(pageSize)) {
            pageSize = 10;
        }

        Pageable adjustedPageable = PageRequest.of(pageable.getPageNumber(), pageSize, pageable.getSort());

        JPAQuery<GetHubResDto> jpaQuery = query.select(getHubProjection())
                .from(qHub)
                .where(whereExpression(searchParam))
                .offset(adjustedPageable.getOffset())
                .limit(adjustedPageable.getPageSize());

        if (adjustedPageable.getSort().isSorted()) {
            for (Sort.Order order : adjustedPageable.getSort()) {
                if (!ALLOWED_SORT_PROPERTIES.contains(order.getProperty())) {
                    continue;
                }
                PathBuilder<Hub> entityPath = new PathBuilder<>(Hub.class, "hub");
                jpaQuery.orderBy(new OrderSpecifier(
                        order.isAscending() ? Order.ASC : Order.DESC,
                        entityPath.get(order.getProperty())
                ));
            }
        } else {
            jpaQuery.orderBy(qHub.createdAt.desc(), qHub.updatedAt.desc());
        }

        JPAQuery<Long> cnt = query
                .select(qHub.count())
                .from(qHub)
                .where(whereExpression(searchParam));

        List<GetHubResDto> results = jpaQuery.fetch();

        return PageableExecutionUtils.getPage(results, adjustedPageable, cnt::fetchOne);
    }

    private QGetHubResDto getHubProjection() {
        return new QGetHubResDto(
                qHub.hubId,
                qHub.hubName,
                qHub.hubAddress,
                qHub.longitude,
                qHub.latitude
        );
    }

    private BooleanBuilder whereExpression(String searchParam) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(searchParam != null){
            booleanBuilder.and(
                    qHub.hubName.contains(searchParam)
                            .or(qHub.hubAddress.contains(searchParam))
            );
        }

        return booleanBuilder;
    }
}
