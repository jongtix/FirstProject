package com.jongtix.book.springboot.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jongtix.book.springboot.domain.posts.QPosts.posts;

@Repository
public class PostsRepositorySupport extends QuerydslRepositorySupport { //querydsl 사용 방법 1
                                                                        //QuerydslRepositorySupport 이용

    private final JPAQueryFactory queryFactory;

    public PostsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Posts.class);
        this.queryFactory = queryFactory;
    }

    public List<Posts> findByAuthor(String author) {
        return queryFactory
                .selectFrom(posts)
                .where(posts.author.eq(author))
                .fetch();
    }

}
