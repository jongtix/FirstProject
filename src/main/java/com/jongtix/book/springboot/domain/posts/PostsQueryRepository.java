package com.jongtix.book.springboot.domain.posts;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.jongtix.book.springboot.domain.posts.QPosts.posts;

@RequiredArgsConstructor
@Repository
public class PostsQueryRepository { //querydsl 사용 방법 3
                                    //상속/구현 없는 Repository

    private final JPAQueryFactory queryFactory;

    public List<Posts> findByAuthor(String author) {
        return queryFactory
                .selectFrom(posts)
                .where(posts.author.eq(author))
                .fetch();
    }

}
