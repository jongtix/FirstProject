package com.jongtix.book.springboot.domain.user;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jongtix.book.springboot.domain.user.QUser.*;

import static com.jongtix.book.springboot.utils.QuerydslUtils.*;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<User> findByName(String name) {
        return queryFactory
                .selectFrom(user)
                .where(user.name.eq(name))
                .fetch();
    }

    @Override
    public List<User> findAllOrderBy(String sort) {
        return queryFactory
                .selectFrom(user)
                .orderBy(getSortedColumn(Order.ASC, user, sort))
                .fetch();
    }

    @Override
    public List<User> findAllOrderByDesc(String sort) {
        return queryFactory
                .selectFrom(user)
                .orderBy(getSortedColumn(Order.DESC, user, sort))
                .fetch();
    }
}
