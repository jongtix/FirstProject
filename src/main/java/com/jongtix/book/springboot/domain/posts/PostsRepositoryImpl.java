package com.jongtix.book.springboot.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jongtix.book.springboot.domain.posts.QPosts.posts;

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
}
