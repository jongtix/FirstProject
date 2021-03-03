package com.jongtix.book.springboot.domain.posts;

import com.querydsl.core.types.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jongtix.book.springboot.domain.posts.QPosts.posts;

import static com.jongtix.book.springboot.utils.QuerydslUtils.*;

@RequiredArgsConstructor
public class PostsRepositoryImpl implements PostsRepositoryCustom { //querydsl 사용 방법 2

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Posts> findByAuthor(String author) {
        return queryFactory
                .selectFrom(posts)
                .where(posts.author.eq(author))
                .fetch();
    }

    @Override
    public List<Posts> findAllOrderBy(String sort) {

        return queryFactory
                .selectFrom(posts)
                .orderBy(getSortedColumn(Order.ASC, posts, sort))
                .fetch();
    }

    @Override
    public List<Posts> findAllOrderByDesc(String sort) {

        return queryFactory
                .selectFrom(posts)
                .orderBy(getSortedColumn(Order.DESC, posts, sort))
                .fetch();
    }

//    @Override
//    public List<Posts> findAllOrderByTitle() {
//        return queryFactory
//                .selectFrom(posts)
//                .orderBy(posts.title.asc())
//                .fetch();
//    }
//
//    @Override
//    public List<Posts> findAllOrderByTitleDesc() {
//        return queryFactory
//                .selectFrom(posts)
//                .orderBy(posts.title.desc())
//                .fetch();
//    }
}
